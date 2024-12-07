FROM openjdk:17-oracle

COPY target/*.jar AuthenticationService-0.0.1-SNAPSHOT.jar

EXPOSE 8085

ENTRYPOINT ["java", "-jar", "AuthenticationService-0.0.1-SNAPSHOT.jar"]