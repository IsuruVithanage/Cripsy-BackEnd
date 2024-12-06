FROM openjdk:17-oracle

COPY target/*.jar EmailService-0.0.1-SNAPSHOT.jar

EXPOSE 8088

ENTRYPOINT ["java", "-jar", "EmailService-0.0.1-SNAPSHOT.jar"]