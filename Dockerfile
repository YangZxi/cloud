FROM eclipse-temurin:17-jre AS build

COPY ./build/cloud-core.jar /app/cloud-core.jar
COPY ./build/application.yml /app/application.yml

EXPOSE 8999

WORKDIR /app

ADD ./build/dist.tar.gz ./public

ENV DB_FILE=/app/.data/cloud.db

CMD ["java", "-jar", "./cloud-core.jar", "--spring.config.additional-location=file:./application.yml"]
