FROM amazoncorretto:17

EXPOSE 8080

ARG JAR_FILE=coffeeshop-app/build/libs/coffeeshop-app-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","/app.jar"]