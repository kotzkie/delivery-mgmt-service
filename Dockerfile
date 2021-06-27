FROM gcr.io/distroless/java:11

ARG JAR_FILE
ADD ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-jar","/app.jar"]
