FROM openjdk:17-jdk-buster
RUN apt-get update && apt-get install -y tzdata
VOLUME /tmp
COPY build/libs/feed-service-0.0.1-SNAPSHOT.jar FeedService.jar
ENTRYPOINT ["java", "-jar", "FeedService.jar"]