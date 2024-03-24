# Usa un'immagine di base Maven con Java 17
FROM maven:3.8.4-openjdk-17 AS build

# Imposta la directory di lavoro all'interno del container
WORKDIR /app

# Copia il codice sorgente nella directory di lavoro
COPY . .

# Compila il progetto
RUN mvn install

# Definisce il comando di avvio dell'applicazione
CMD ["java", "-jar", "target/gs-minesweeper-1.0-SNAPSHOT.jar"]
