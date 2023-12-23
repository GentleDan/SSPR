FROM openjdk:17-jdk-slim
FROM maven:3.9.6
WORKDIR /app
COPY Project message
