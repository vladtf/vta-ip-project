terraform {
  backend "s3" {
    bucket = "terraform-state-bucket-vta-project"
    key    = "terraform.tfstate"
    region = "eu-central-1"
  }
}

provider "random" {
  version = "3.5.1"
}

provider "aws" {
  region = var.region
}

resource "aws_ecr_repository" "backend_repository" {
  name = "backend_repository"
}

resource "aws_ecr_repository" "frontend_repository" {
  name = "frontend_repository"
}

resource "aws_vpc" "app_vpc" {
  cidr_block           = "10.0.0.0/16"
  enable_dns_hostnames = true
  enable_dns_support   = true

  tags = {
    Name = "app_vpc"
  }
}

resource "aws_subnet" "subnet_1" {
  cidr_block        = "10.0.1.0/24"
  vpc_id            = aws_vpc.app_vpc.id
  availability_zone = "eu-central-1a"

  tags = {
    Name = "public_subnet_1"
  }

  depends_on = [aws_vpc.app_vpc]
}

resource "aws_subnet" "subnet_2" {
  cidr_block        = "10.0.2.0/24"
  vpc_id            = aws_vpc.app_vpc.id
  availability_zone = "eu-central-1b"

  tags = {
    Name = "public_subnet_2"
  }

  depends_on = [aws_vpc.app_vpc]
}

resource "aws_internet_gateway" "app_igw" {
  vpc_id = aws_vpc.app_vpc.id

  tags = {
    Name = "app_igw"
  }

  depends_on = [aws_vpc.app_vpc]
}

resource "aws_route_table" "public_route_table" {
  vpc_id = aws_vpc.app_vpc.id

  route {
    cidr_block = "0.0.0.0/0"
    gateway_id = aws_internet_gateway.app_igw.id
  }

  tags = {
    Name = "public_route_table"
  }

  depends_on = [aws_vpc.app_vpc, aws_internet_gateway.app_igw]
}

resource "aws_route_table_association" "public_route_association_1" {
  subnet_id      = aws_subnet.subnet_1.id
  route_table_id = aws_route_table.public_route_table.id

  depends_on = [aws_subnet.subnet_1, aws_route_table.public_route_table]
}

resource "aws_route_table_association" "public_route_association_2" {
  subnet_id      = aws_subnet.subnet_2.id
  route_table_id = aws_route_table.public_route_table.id

  depends_on = [aws_subnet.subnet_2, aws_route_table.public_route_table]
}

resource "aws_lb" "vta_load_balancer" {
  name               = "vta-load-balancer"
  internal           = false
  load_balancer_type = "network"
  subnets            = [aws_subnet.subnet_1.id, aws_subnet.subnet_2.id]
  idle_timeout       = 300

  tags = {
    Name = "vta_load_balancer"
  }

  depends_on = [aws_subnet.subnet_1, aws_subnet.subnet_2]
}

resource "aws_lb_target_group" "frontend_lb_tg" {
  name        = "frontend-lb-tg"
  port        = 80
  protocol    = "TCP"
  vpc_id      = aws_vpc.app_vpc.id
  target_type = "ip"

  health_check {
    protocol            = "TCP"
    port                = "80"
    interval            = 30
    timeout             = 5
    healthy_threshold   = 2
    unhealthy_threshold = 5
  }

  depends_on = [aws_vpc.app_vpc]
}

resource "aws_lb_target_group" "backend_lb_tg" {
  name        = "backend-lb-tg"
  port        = 8090
  protocol    = "TCP"
  vpc_id      = aws_vpc.app_vpc.id
  target_type = "ip"

  health_check {
    protocol            = "TCP"
    port                = "8090"
    interval            = 30
    timeout             = 5
    healthy_threshold   = 2
    unhealthy_threshold = 5
  }

  depends_on = [aws_vpc.app_vpc]
}


resource "aws_lb_listener" "frontend_lb_listener" {
  load_balancer_arn = aws_lb.vta_load_balancer.arn
  port              = 80
  protocol          = "TCP"

  default_action {
    type             = "forward"
    target_group_arn = aws_lb_target_group.frontend_lb_tg.arn
  }

  depends_on = [aws_lb.vta_load_balancer, aws_lb_target_group.frontend_lb_tg]
}

resource "aws_lb_listener" "backend_lb_listener" {
  load_balancer_arn = aws_lb.vta_load_balancer.arn
  port              = 8090
  protocol          = "TCP"

  default_action {
    type             = "forward"
    target_group_arn = aws_lb_target_group.backend_lb_tg.arn
  }

  depends_on = [aws_lb.vta_load_balancer, aws_lb_target_group.backend_lb_tg]
}

resource "aws_iam_role" "ecs_task_execution_role" {
  name = "ecs-task-execution-role"
  assume_role_policy = jsonencode({
    Version = "2012-10-17"
    Statement = [
      {
        Action = "sts:AssumeRole"
        Effect = "Allow"
        Principal = {
          Service = "ecs-tasks.amazonaws.com"
        }
      }
    ]
  })

  tags = {
    Name = "ecs_task_execution_role"
  }
}


resource "aws_ecs_task_definition" "frontend_task" {
  family       = "frontend_task"
  network_mode = "awsvpc"

  cpu    = 256 # set the CPU units for the task
  memory = 512 # set the memory limit for the task

  container_definitions = jsonencode([
    {
      name   = "frontend-container"
      image  = aws_ecr_repository.frontend_repository.repository_url
      memory = 128 # set the memory limit for the container
      extra_hosts = [
        {
          hostname = "backend"
          ip       = aws_lb.vta_load_balancer.dns_name
        }
      ]

      portMappings = [
        {
          containerPort = 80
          protocol      = "tcp"
        }
      ]
      logConfiguration = {
        logDriver = "awslogs"
        options = {
          "awslogs-group"         = "/ecs/frontend_task"
          "awslogs-region"        = "eu-central-1"
          "awslogs-stream-prefix" = "frontend-container"
        }
      }
    }
  ])

  requires_compatibilities = [
    "FARGATE" # specify that the task definition is compatible with Fargate
  ]

  execution_role_arn = aws_iam_role.ecs_task_execution_role.arn

  depends_on = [aws_iam_role.ecs_task_execution_role]
}

resource "aws_ecs_task_definition" "backend_task" {
  family       = "backend_task"
  network_mode = "awsvpc"

  cpu    = 256 # set the CPU units for the task
  memory = 512 # set the memory limit for the task

  container_definitions = jsonencode([
    {
      name   = "backend-container"
      image  = aws_ecr_repository.backend_repository.repository_url
      memory = 256 # set the memory limit for the container
      portMappings = [
        {
          containerPort = 8090
          protocol      = "tcp"
        }
      ]
      logConfiguration = {
        logDriver = "awslogs"
        options = {
          "awslogs-group"         = "/ecs/backend_task"
          "awslogs-region"        = "eu-central-1"
          "awslogs-stream-prefix" = "backend-container"
        }
      }
      environment = [
        {
          name  = "SPRING_DATASOURCE_URL"
          value = "jdbc:mariadb://mariadb-service.vta-namespace:3306/vta_database"
        },
        {
          name  = "SPRING_DATASOURCE_USERNAME"
          value = "myuser"
        },
        {
          name  = "SPRING_DATASOURCE_PASSWORD"
          value = random_password.mariadb_password.result
        }
      ]

      # healthCheck = {
      #   command     = ["CMD-SHELL", "curl --fail http://localhost:8090/login/alive || exit 1"]
      #   interval    = 30
      #   timeout     = 5
      #   startPeriod = 60
      #   retries     = 3
      # }

    }
  ])

  requires_compatibilities = [
    "FARGATE" # specify that the task definition is compatible with Fargate
  ]

  execution_role_arn = aws_iam_role.ecs_task_execution_role.arn

  depends_on = [aws_iam_role.ecs_task_execution_role]
}

resource "random_password" "mariadb_password" {
  length           = 16
  special          = true
  override_special = "_%@"
}


resource "aws_ecs_task_definition" "mariadb_task" {
  family       = "mariadb_task"
  network_mode = "awsvpc"

  cpu    = 256 # set the CPU units for the task
  memory = 512 # set the memory limit for the task

  container_definitions = jsonencode([
    {
      name   = "mariadb-container"
      image  = "mariadb:latest" # specify the MariaDB image
      memory = 256              # set the memory limit for the container
      portMappings = [
        {
          containerPort = 3306 # MariaDB default port
          protocol      = "tcp"
        }
      ]
      environment = [{
        name  = "MARIADB_DATABASE"
        value = "vta_database"
        }, {
        name  = "MARIADB_ROOT_PASSWORD"
        value = random_password.mariadb_password.result
        }, {
        name  = "MARIADB_USER"
        value = "myuser"
        }, {
        name  = "MARIADB_PASSWORD"
        value = random_password.mariadb_password.result
        }, {
        name  = "MYSQL_RANDOM_ROOT_PASSWORD"
        value = "false"
        }

      ]
      logConfiguration = {
        logDriver = "awslogs"
        options = {
          "awslogs-group"         = "/ecs/mariadb_task"
          "awslogs-region"        = "${var.region}"
          "awslogs-stream-prefix" = "mariadb-container"
        }
      }
    }
  ])

  requires_compatibilities = [
    "FARGATE" # specify that the task definition is compatible with Fargate
  ]

  execution_role_arn = aws_iam_role.ecs_task_execution_role.arn

  depends_on = [aws_iam_role.ecs_task_execution_role]
}

resource "aws_ecs_cluster" "vta_cluster" {
  name = "vta-cluster"
}

resource "aws_ecs_service" "frontend_service" {
  name            = "frontend-service"
  cluster         = aws_ecs_cluster.vta_cluster.id
  task_definition = aws_ecs_task_definition.frontend_task.arn

  launch_type = "FARGATE"

  network_configuration {
    assign_public_ip = true
    subnets          = [aws_subnet.subnet_1.id, aws_subnet.subnet_2.id]
    security_groups  = [aws_security_group.frontend_sg.id]
  }

  load_balancer {
    target_group_arn = aws_lb_target_group.frontend_lb_tg.arn
    container_name   = "frontend-container"
    container_port   = 80
  }

  platform_version = "1.4.0"

  deployment_controller {
    type = "ECS"
  }

  # Set desired_count to 1 for a single task
  desired_count = 1

  # Use the Auto Scaling group instead of desired_count
  scheduling_strategy = "REPLICA"

  depends_on = [
    aws_ecs_cluster.vta_cluster, aws_ecs_task_definition.frontend_task, aws_lb_target_group.frontend_lb_tg,
    aws_security_group.frontend_sg
  ]
}

resource "aws_ecs_service" "backend_service" {
  name            = "backend-service"
  cluster         = aws_ecs_cluster.vta_cluster.id
  task_definition = aws_ecs_task_definition.backend_task.arn

  launch_type = "FARGATE"

  network_configuration {
    assign_public_ip = true
    subnets          = [aws_subnet.subnet_1.id, aws_subnet.subnet_2.id]
    security_groups  = [aws_security_group.backend_sg.id]
  }

  platform_version = "1.4.0"

  deployment_controller {
    type = "ECS"
  }

  # Set desired_count to 1 for a single task
  desired_count = 1

  # Use the Auto Scaling group instead of desired_count
  scheduling_strategy = "REPLICA"

  # Add a health check to the service
  # health_check_grace_period_seconds = 60

  service_registries {
    registry_arn = aws_service_discovery_service.backend_service.arn
  }

  load_balancer {
    target_group_arn = aws_lb_target_group.backend_lb_tg.arn
    container_name   = "backend-container"
    container_port   = 8090
  }

  depends_on = [
    aws_ecs_cluster.vta_cluster, aws_ecs_task_definition.backend_task, #aws_lb_target_group.backend_lb_tg,
    aws_security_group.backend_sg
  ]
}

resource "aws_ecs_service" "mariadb_service" {
  name            = "mariadb-service"
  cluster         = aws_ecs_cluster.vta_cluster.id
  task_definition = aws_ecs_task_definition.mariadb_task.arn

  launch_type = "FARGATE"

  network_configuration {
    assign_public_ip = true
    subnets          = [aws_subnet.subnet_1.id, aws_subnet.subnet_2.id]
    security_groups  = [aws_security_group.mariadb_sg.id]
  }

  platform_version = "1.4.0"

  deployment_controller {
    type = "ECS"
  }
  # Set desired_count to 1 for a single task
  desired_count = 1

  # Use the Auto Scaling group instead of desired_count
  scheduling_strategy = "REPLICA"


  service_registries {
    registry_arn = aws_service_discovery_service.mariadb_service.arn
  }

  depends_on = [
    aws_ecs_cluster.vta_cluster, aws_ecs_task_definition.mariadb_task,
    aws_security_group.mariadb_sg
  ]
}

resource "aws_service_discovery_private_dns_namespace" "vta_private_dns_namespace" {
  name = "vta-namespace"
  vpc  = aws_vpc.app_vpc.id
}

resource "aws_service_discovery_service" "mariadb_service" {
  name = "mariadb-service"
  dns_config {
    namespace_id = aws_service_discovery_private_dns_namespace.vta_private_dns_namespace.id
    dns_records {
      ttl  = 10
      type = "A"
    }

    routing_policy = "MULTIVALUE"
  }

  health_check_custom_config {
    failure_threshold = 1
  }
}

resource "aws_service_discovery_service" "backend_service" {
  name = "backend-service"
  dns_config {
    namespace_id = aws_service_discovery_private_dns_namespace.vta_private_dns_namespace.id
    dns_records {
      ttl  = 10
      type = "A"
    }

    routing_policy = "MULTIVALUE"
  }

  health_check_custom_config {
    failure_threshold = 1
  }
}


resource "aws_iam_role_policy_attachment" "ecs_task_execution_role_ecr_policy" {
  policy_arn = "arn:aws:iam::aws:policy/AmazonEC2ContainerRegistryReadOnly"
  role       = aws_iam_role.ecs_task_execution_role.name

  depends_on = [aws_iam_role.ecs_task_execution_role]
}


resource "aws_iam_role_policy_attachment" "ecs_task_execution_role_policy_attachment" {
  policy_arn = "arn:aws:iam::aws:policy/service-role/AmazonECSTaskExecutionRolePolicy"
  role       = aws_iam_role.ecs_task_execution_role.name

  depends_on = [aws_iam_role.ecs_task_execution_role]
}

resource "aws_iam_role_policy_attachment" "ecs_task_execution_logs" {
  policy_arn = "arn:aws:iam::aws:policy/CloudWatchLogsFullAccess"
  role       = aws_iam_role.ecs_task_execution_role.name

  depends_on = [aws_iam_role.ecs_task_execution_role]
}

resource "aws_cloudwatch_log_group" "frontend_task_log_group" {
  name              = "/ecs/frontend_task"
  retention_in_days = 7

}

resource "aws_cloudwatch_log_group" "backend_task_log_group" {
  name              = "/ecs/backend_task"
  retention_in_days = 7

}

resource "aws_cloudwatch_log_group" "mariadb_task_log_group" {
  name              = "/ecs/mariadb_task"
  retention_in_days = 7

}

resource "aws_security_group" "frontend_sg" {
  name_prefix = "frontend_sg"
  vpc_id      = aws_vpc.app_vpc.id

  ingress {
    from_port   = 80
    to_port     = 80
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port   = 0
    to_port     = 65535
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = {
    Name = "frontend_sg"
  }

  depends_on = [aws_vpc.app_vpc]
}

resource "aws_security_group" "backend_sg" {
  name_prefix = "backend_sg"
  vpc_id      = aws_vpc.app_vpc.id

  ingress {
    from_port   = 8090
    to_port     = 8090
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port   = 0
    to_port     = 65535
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = {
    Name = "backend_sg"
  }

  depends_on = [aws_vpc.app_vpc]
}

resource "aws_security_group" "mariadb_sg" {
  name_prefix = "mariadb_sg"
  vpc_id      = aws_vpc.app_vpc.id

  ingress {
    from_port   = 3306
    to_port     = 3306
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port   = 0
    to_port     = 65535
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = {
    Name = "frontend_sg"
  }

  depends_on = [aws_vpc.app_vpc]
}
