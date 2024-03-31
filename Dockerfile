FROM openjdk:17
EXPOSE 8080
ADD target/se-service.jar se-service.jar
ENTRYPOINT ["java", "-jar", "/se-service.jar"]
