FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY ./target/service_edu.jar service_edu.jar
ENTRYPOINT ["java","-jar","/service_edu.jar", "&"]