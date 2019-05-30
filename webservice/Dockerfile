
FROM java
WORKDIR /
ADD target/webservice-0.0.3-SNAPSHOT.jar webservice.jar
EXPOSE 8080
CMD ["java", "-jar", "webservice.jar"]