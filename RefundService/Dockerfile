FROM openjdk:17-oracle

COPY target/*.jar RefundService-0.0.1-SNAPSHOT.jar

EXPOSE 8089

ENTRYPOINT ["java", "-jar", "RefundService-0.0.1-SNAPSHOT.jar"]