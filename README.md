# Program Engineering Project - VTA

## Table of Contents

- [Program Engineering Project - VTA](#program-engineering-project---vta)
  - [Table of Contents](#table-of-contents)
  - [Documentation](#documentation)
  - [Presentations](#presentations)
  - [Deploy](#deploy)
  - [Local Environment](#local-environment)
    - [Run frontend](#run-frontend)
    - [Run backend](#run-backend)
    - [Run Docker Compose](#run-docker-compose)
    - [Connect to the database](#connect-to-the-database)
  - [Demo](#demo)

## Documentation

- [Requirements Specification Document](documentation/Requirements%20Specification%20Document.md)
- [Architectural Design Document](documentation/Arhitectural%20Design%20Document.md)
- [Sprint 1 - Review](documentation/Sprint%201%20-%20Review.md)
- [Sprint 2 - Review](documentation/Sprint%202%20-%20Review.md)
- [Sprint 3 - Review](documentation/Sprint%203%20-%20Review.md)

## Presentations

- [CI CD Presentation](prezentation/ci-cd-presentation.pdf)
- [Final Presentation](presentation/final-presentation.pptx)

## Deploy

The application is deployed on AWS.

To deploy the entire infrastructure follow the steps below:

- Run `Terraform Apply` in GitHub Actions
- Run `Build and Push to ECR Backend` in GitHub Actions
- Run `Build and Push to ECR Frontend` in GitHub Actions

To remove the entire infrastructure follow the steps below:

- Run `Terraform Destroy` in GitHub Actions

## Local Environment

### Run frontend

Node.js and npm are required.

```bash
❯ npm --version
9.5.0
❯ node --version
v19.7.0
```

Update the packages

```bash
> cd frontend
> npm install
```

Run the frontend

```bash
> npm start
```

### Run backend

Start the backend.

> Note: You need to have Java 11 installed on your machine. You can check the version by running `java -version`.
> Note: The database is not running locally. You need to run the docker-compose.

```bash
> cd backend
> ./mvnw spring-boot:run
```

### Run Docker Compose

You need docker and docker-compose installed on your machine.

```bash
# check if docker is installed
❯ docker --version
Docker version 20.10.20, build 9fdeb9c

# check if docker-compose is installed
❯ docker-compose --version
Docker Compose version v2.12.0

# check if docker is running
❯ docker ps
CONTAINER ID   IMAGE     COMMAND   CREATED   STATUS    PORTS     NAMES

# run docker-compose
❯ docker-compose up
[+] Running 3/3
 ⠿ Network vta-ip-project_default        Created                                                                                                                                                             0.2s
 ⠿ Volume "vta-ip-project_mariadb-data"  Created                                                                                                                                                             0.0s
 ⠿ Container mariadb_container           Created                                                                                                                                                             0.1s
Attaching to mariadb_container
...
mariadb_container  | 2023-04-20  7:15:36 0 [Note] mariadbd: ready for connections.
mariadb_container  | Version: '10.11.2-MariaDB-1:10.11.2+maria~ubu2204'  socket: '/run/mysqld/mysqld.sock'  port: 3306  mariadb.org binary distribution
```

### Connect to the database

You need to have mysql installed on your machine.

```bash
> mysql -h 127.0.0.1 -P 3306 -u myuser -p'mypassword'  mydatabase

mysql> SHOW tables in  mydatabase;
+----------------------+
| Tables_in_mydatabase |
+----------------------+
| users                |
+----------------------+
1 row in set (0.01 sec)
```

## Demo

Mail Server:

![](documentation/materials/sprint3/mailosaur.png)

Activation Email:

![](documentation/materials/sprint3/activation_email.png)

Accounts Page:

![](documentation/materials/sprint3/accounts_page.png)

Admin Page:

![](documentation/materials/sprint3/admin_page.png)

Ecs Cluster:

![](documentation/materials/sprint3/ecs_cluster.png)

Home Page:

![](documentation/materials/sprint3/home_page.png)

Transactions Page:

![](documentation/materials/sprint3/transactions_page.png)

Login Page:

![](documentation/materials/sprint3/login_page.png)

Register Page:

![](documentation/materials/sprint3/register_page.png)
