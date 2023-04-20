# Program Engineering Project - VTA

## Table of Contents

- [Program Engineering Project - VTA](#program-engineering-project---vta)
  - [Table of Contents](#table-of-contents)
  - [Documentation](#documentation)
  - [Local Environment](#local-environment)
    - [Run Docker Compose](#run-docker-compose)
    - [Connect to the database](#connect-to-the-database)

## Documentation

- [Requirements Specification Document](documentation/Requirements%20Specification%20Document.md)
- [Arhitectural Design Document](documentation/Arhitectural%20Design%20Document.md)

## Local Environment

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
