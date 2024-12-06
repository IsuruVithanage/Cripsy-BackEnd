FROM openjdk:17-oracle

COPY target/*.jar OrderService-0.0.1-SNAPSHOT.jar

EXPOSE 8083

ENTRYPOINT ["java", "-jar", "OrderService-0.0.1-SNAPSHOT.jar"]