FROM ubuntu:latest AS build

RUN apt-get update && \
apt-get install -y openjdk-17-jdk maven && \
apt-get clean;

RUN mkdir -p /app/uploadedImages/practiceBaseImages
RUN mkdir -p /app/uploadedImages/profileImages

WORKDIR /app

COPY . .

RUN mvn clean package

FROM openjdk:17-jdk-slim

EXPOSE 8080

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
