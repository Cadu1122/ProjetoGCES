FROM maven:latest

WORKDIR /app

COPY src /app/src
COPY pom.xml /app

RUN mvn clean install

CMD ["java", "-jar", "target/ProjetoGCES-1.0-SNAPSHOT.jar"]

