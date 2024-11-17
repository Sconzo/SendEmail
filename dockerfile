# Etapa 1: Build do projeto com Maven
FROM maven:3.9.4-openjdk-21 AS builder

# Diretório de trabalho no container
WORKDIR /app

# Copiar os arquivos do projeto para dentro do container
COPY pom.xml .
COPY src ./src

# Executar o build do Maven para gerar o JAR
RUN mvn clean package -DskipTests

# Etapa 2: Configuração do container para execução
FROM openjdk:21-jdk

# Diretório de trabalho no container
WORKDIR /app

# Copiar o JAR gerado da etapa anterior
COPY --from=builder /app/target/*.jar app.jar

# Porta exposta (ajuste conforme sua aplicação)
EXPOSE 8080

# Comando para rodar a aplicação
CMD ["java", "-jar", "app.jar"]
