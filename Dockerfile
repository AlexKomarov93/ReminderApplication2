FROM gradle:8.14-jdk21 AS builder
WORKDIR /app
COPY build.gradle .
COPY settings.gradle .

RUN gradle dependencies --refresh-dependencies

COPY src ./src
RUN gradle assemble --no-daemon
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]