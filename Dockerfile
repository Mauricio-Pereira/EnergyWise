
# Etapa 1: Construção da aplicação
FROM gradle:jdk21 AS builder
LABEL authors="Mauricio.Pereira"
WORKDIR /app
COPY . .
RUN gradle build --no-daemon -x test


# Etapa 2: Preparação da imagem final
FROM openjdk:21-jdk-slim AS runtime
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
