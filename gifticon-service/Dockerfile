FROM openjdk:17-jdk-buster
RUN apt-get update && apt-get install -y tzdata
VOLUME /tmp
COPY build/libs/gifticon-service-0.0.1-SNAPSHOT.jar GifticonService.jar
ENTRYPOINT ["java", "-jar", "GifticonService.jar"]