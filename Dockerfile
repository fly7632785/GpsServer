# FROM adoptopenjdk/openjdk8:ubi
# openj9内存会小很多
FROM adoptopenjdk:8-jdk-openj9

VOLUME /tmp
COPY target/demo-0.0.1-SNAPSHOT.jar app.jar
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]