FROM radut/openjdk-17-maven
RUN apt-get update && \
    apt-get install -y curl && \
    mkdir /app/cd-account-microservice
WORKDIR /app/cd-account-microservice
ADD target/*.jar cd-account-microservice.jar
EXPOSE 5051
ENTRYPOINT java -jar cd-account-microservice.jar --spring.profiles.active=$ENV_PROFILE
