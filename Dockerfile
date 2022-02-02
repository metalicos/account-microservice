FROM openjdk:17-oracle
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} cd-account-microservice.jar
EXPOSE 5051
ENTRYPOINT ["java","-jar","/cd-account-microservice.jar"]