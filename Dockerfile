FROM adoptopenjdk/openjdk8:latest
RUN mkdir /tmp/exports
ADD target/dis-mensajes-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT [ "sh", "-c", "java -jar /app.jar" ]