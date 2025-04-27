# ---- Build the application with JDK 21 ----
FROM gradle:8.4.0-jdk21 AS builder
COPY . /home/app
WORKDIR /home/app
RUN gradle bootJar --no-daemon

# ---- Run the application with lightweight JDK 21 image ----
FROM openjdk:21-jdk-slim
WORKDIR /app
COPY --from=builder /home/app/build/libs/*.jar app.jar
COPY model /app/model

# порт, на котором работает сервис
EXPOSE 8086

ENTRYPOINT ["java", "-jar", "app.jar"]