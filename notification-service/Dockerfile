FROM openjdk:17-jdk-buster
RUN apt-get update && apt-get install -y tzdata
VOLUME /tmp
COPY build/libs/notification-service-0.0.1-SNAPSHOT.jar NotificationService.jar
ENTRYPOINT ["java", "-jar", "NotificationService.jar"]