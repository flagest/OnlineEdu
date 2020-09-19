FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY ./target/service_oss.jar service_oss.jar
ENTRYPOINT ["java","-jar","/service_oss.jar", "&"]