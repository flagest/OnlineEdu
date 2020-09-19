FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY ./target/service_statistics.jar service_statistics.jar
ENTRYPOINT ["java","-jar","/service_statistics.jar", "&"]