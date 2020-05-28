通过 Docker 启动 MongoDB

官⽅指引

https://hub.docker.com/_/mongo 


获取镜像

docker pull mongo 


运⾏ MongoDB 镜像

docker run --name mongo -p 27017:27017 -v ~/dockerdata/mongo:/data/db -e MONGO_INITDB_ROOT_USERNAME=admin -e MONGO_INITDB_ROOT_PASSWORD=admin -d mongo




通过 Docker 启动 Redis

官⽅指引

https://hub.docker.com/_/redis


获取镜像

docker pull redis 


启动 Redis

docker run --name redis -d -p 6379:6379 redis