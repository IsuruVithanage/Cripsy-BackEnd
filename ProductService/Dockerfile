FROM openjdk:17-oracle

COPY target/*.jar ProductService-0.0.1-SNAPSHOT.jar

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "ProductService-0.0.1-SNAPSHOT.jar"]