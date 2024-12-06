FROM openjdk:17-oracle

COPY target/*.jar CustomerService-0.0.1-SNAPSHOT.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "CustomerService-0.0.1-SNAPSHOT.jar"]