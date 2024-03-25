# Stage 1: Build dell'applicazione
FROM maven:3.8.4-openjdk-17 AS build

WORKDIR /app

# Copia solo il file POM per risolvere le dipendenze
COPY pom.xml .

# Esegue la fase di installazione delle dipendenze senza copiare l'intero contesto di build
RUN mvn dependency:go-offline

# Copia il resto del codice sorgente e compila l'applicazione
COPY src ./src
RUN mvn package -DskipTests

# Stage 2: Esecuzione dell'applicazione
FROM openjdk:17-jdk-slim

WORKDIR /app

# Copia solo il file JAR risultante dallo stage di build
COPY --from=build /app/target/gs-minesweeper-1.0-SNAPSHOT.jar .

# Definisce il comando di avvio dell'applicazione
CMD ["java", "-jar", "gs-minesweeper-1.0-SNAPSHOT.jar"]
