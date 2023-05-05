terraform {
  backend "s3" {
    bucket = "terraform-state-bucket-vta-project"
    key    = "terraform.tfstate"
    region = "eu-central-1"
  }
}

provider "aws" {
  region = "eu-central-1"
}

resource "aws_ecr_repository" "backend_repository" {
  name = "backend_repository"
}

resource "aws_ecr_repository" "frontend_repository" {
  name = "frontend_repository"
}

resource "aws_vpc" "app_vpc" {
  cidr_block = "10.0.0.0/16"

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

resource "aws_security_group" "app_lb_sg" {
  name_prefix = "app-lb-sg"
  vpc_id      = aws_vpc.app_vpc.id

  ingress {
    from_port   = 0
    to_port     = 65535
    protocol    = "tcp"
    cidr_blocks = ["188.25.166.255/32"]
  }

  depends_on = [aws_vpc.app_vpc]
}

resource "aws_lb" "app_lb" {
  name               = "app-lb"
  internal           = false
  load_balancer_type = "application"
  subnets            = [aws_subnet.subnet_1.id, aws_subnet.subnet_2.id]
  security_groups    = [aws_security_group.frontend_service_sg.id]

  tags = {
    Name = "app_lb"
  }

  depends_on = [aws_subnet.subnet_1, aws_subnet.subnet_2, aws_security_group.frontend_service_sg]
}

resource "aws_lb_target_group" "app_lb_tg" {
  name        = "app-lb-tg"
  port        = 80
  protocol    = "HTTP"
  vpc_id      = aws_vpc.app_vpc.id
  target_type = "ip"

  # TODO: Uncomment when health check is implemented
  #  health_check {
  #    path                = "/"
  #  }

  depends_on = [aws_vpc.app_vpc]
}


resource "aws_lb_listener" "app_lb_listener" {
  load_balancer_arn = aws_lb.app_lb.arn
  port              = 80
  protocol          = "HTTP"

  default_action {
    type             = "forward"
    target_group_arn = aws_lb_target_group.app_lb_tg.arn
  }

  depends_on = [aws_lb.app_lb, aws_lb_target_group.app_lb_tg]
}

resource "aws_iam_role" "ecs_task_execution_role" {
  name               = "ecs-task-execution-role"
  assume_role_policy = jsonencode({
    Version   = "2012-10-17"
    Statement = [
      {
        Action    = "sts:AssumeRole"
        Effect    = "Allow"
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

  cpu    = 256   # set the CPU units for the task
  memory = 512   # set the memory limit for the task

  container_definitions = jsonencode([
    {
      name         = "frontend-container"
      image        = aws_ecr_repository.frontend_repository.repository_url
      memory       = 128   # set the memory limit for the container
      portMappings = [
        {
          containerPort = 80
          protocol      = "tcp"
        }
      ]
      healthCheck = {
        command  = ["CMD-SHELL", "curl -f http://localhost || exit 1"]
        interval = 30
        timeout  = 5
        retries  = 3
      }
      logConfiguration = {
        logDriver = "awslogs"
        options   = {
          "awslogs-group"         = "/ecs/frontend_task"
          "awslogs-region"        = "eu-central-1"
          "awslogs-stream-prefix" = "frontend-container"
        }
      }
    }
  ])

  requires_compatibilities = [
    "FARGATE"   # specify that the task definition is compatible with Fargate
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
    security_groups  = [aws_security_group.frontend_service_sg.id]
  }

  load_balancer {
    target_group_arn = aws_lb_target_group.app_lb_tg.arn
    container_name   = "frontend-container"
    container_port   = 80
  }

  platform_version = "1.4.0"

  deployment_controller {
    type = "ECS"
  }

  # Use the Auto Scaling group instead of desired_count
  scheduling_strategy = "REPLICA"

  depends_on = [
    aws_ecs_cluster.vta_cluster, aws_ecs_task_definition.frontend_task, aws_lb_target_group.app_lb_tg,
    aws_security_group.frontend_service_sg
  ]
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

  depends_on = [aws_ecs_service.frontend_service]
}

resource "aws_security_group" "frontend_service_sg" {
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
