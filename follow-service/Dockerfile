FROM openjdk:17-jdk-buster
RUN apt-get update && apt-get install -y tzdata
VOLUME /tmp
COPY build/libs/follow-service-0.0.1-SNAPSHOT.jar FollowService.jar
ENTRYPOINT ["java", "-jar", "FollowService.jar"]