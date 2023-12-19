FROM openjdk:17-jdk-buster
RUN apt-get update && apt-get install -y tzdata
VOLUME /tmp
COPY build/libs/apigateway-service-0.0.1-SNAPSHOT.jar ApiGatewayService.jar
ENTRYPOINT ["java", "-jar", "ApiGatewayService.jar"]