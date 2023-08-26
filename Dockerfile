FROM eclipse-temurin:17-jdk-focal

COPY target/examen2-0.0.1-SNAPSHOT.jar examen2-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/examen2-0.0.1-SNAPSHOT.jar"]