FROM openjdk:17-jdk-buster
RUN apt-get update && apt-get install -y tzdata
VOLUME /tmp
COPY build/libs/discovery-service-0.0.1-SNAPSHOT.jar DiscoveryService.jar
ENTRYPOINT ["java", "-jar", "DiscoveryService.jar"]