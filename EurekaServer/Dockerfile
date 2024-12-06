FROM openjdk:17-oracle

COPY target/*.jar EurekaServer-0.0.1-SNAPSHOT.jar

EXPOSE 8761

ENTRYPOINT ["java", "-jar", "EurekaServer-0.0.1-SNAPSHOT.jar"]