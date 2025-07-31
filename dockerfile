# Etapa de construcción
FROM eclipse-temurin:17-jdk-alpine as builder
WORKDIR /app

# Copiamos archivos del proyecto
COPY pom.xml ./
COPY src ./src

# Instalamos Maven y construimos el proyecto
RUN ./mvnw clean package -DskipTests

# Etapa final
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copiamos el jar construido
COPY --from=builder /app/target/cotizador-*.jar app.jar

# Puerto por defecto
EXPOSE 8081

# Comando de ejecución
ENTRYPOINT ["java", "-jar", "app.jar"]
