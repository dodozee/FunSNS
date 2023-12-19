FROM openjdk:17-jdk-buster
RUN apt-get update && apt-get install -y tzdata
VOLUME /tmp
COPY build/libs/user-service-0.0.1-SNAPSHOT.jar UserService.jar
ENTRYPOINT ["java", "-jar", "UserService.jar"]
