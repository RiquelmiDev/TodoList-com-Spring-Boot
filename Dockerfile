FROM maven:3.9.4-eclipse-temurin-21 AS build

WORKDIR /app

# Atualiza pacotes do sistema no estágio de build para mitigar vulnerabilidades conhecidas
RUN apt-get update && apt-get upgrade -y && rm -rf /var/lib/apt/lists/*

# Copia apenas o pom e o código fonte para aproveitar cache do Docker
COPY pom.xml .
COPY src ./src

# Build do jar (skip tests para builds mais rápidos
RUN mvn -B -DskipTests package

FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

# Atualiza pacotes do sistema no estágio de runtime para reduzir vulnerabilidades conhecidas
RUN apt-get update && apt-get upgrade -y && rm -rf /var/lib/apt/lists/*

EXPOSE 8080

# Copia o artefato gerado na etapa de build
COPY --from=build /app/target/*.jar app.jar

ENTRYPOINT ["java","-jar","app.jar"]