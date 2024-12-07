FROM openjdk:17-oracle

COPY target/*.jar ApiGateway-0.0.1-SNAPSHOT.jar

EXPOSE 8085

ENTRYPOINT ["java", "-jar", "ApiGateway-0.0.1-SNAPSHOT.jar"]