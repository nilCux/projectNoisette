# Project Noisette

## Tech note  

This is a project built with a structure like:  
FrontEnd <---> BFF layer <--> BackEnd

- FrontEnd:
    - Tech used:  
        React, Axios  
- BFF:  
    - Tech used:  
        NodeJs, Koa, Axios, (nginx)
- BackEnd:
    - Tech Used:  
        Java,SpringBoot, MongoDB, Redis


## Get ready to go:  

- Run MongoDB in docker

    > Official  instruction  
    https://hub.docker.com/_/mongo  

    - Pull MongoDB image  
    `docker pull mongo`

    - Run MongoDB image in a container 
    `docker run --name noisette_db -p 27017:27017 -v ~/dockerdata/mongo:/data/db -e MONGO_INITDB_ROOT_USERNAME=admin -e MONGO_INITDB_ROOT_PASSWORD=admin -d mongo`

    - Log into MongoDB shell to add a database, add a user and password for this new database. Set these information to corresponding properties in:  
    `./backEnd/src/main/resources/application.properties`  
    - 登录到 MongoDB 容器中  
    docker exec -it noisette_db bash  
    - 通过 Shell 连接 MongoDB  
    mongo -u admin -p admin  

- 通过 Docker 启动 Redis

    > 官⽅指引  
    https://hub.docker.com/_/redis


    - 获取镜像
    `docker pull redis `


    - 启动 Redis

    `docker run --name redis -d -p 6379:6379 redis`

- Run Backend
- Run BFF

- Check `127.0.0.1:3000` , it should be alive.