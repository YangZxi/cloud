FROM eclipse-temurin:17-jre AS build

# 使用 Redis 官方镜像
# FROM redis:latest AS redis

COPY ./build/cloud-core.jar /app/cloud-core.jar
COPY ./build/application.yml /app/application.yml

EXPOSE 8999

# CMD ["redis-server", "--daemonize yes"]

WORKDIR /app

ADD ./build/dist.tar.gz ./public

CMD ["java", "-jar", "./cloud-core.jar", "--spring.config.additional-location=file:./application.yml"]
