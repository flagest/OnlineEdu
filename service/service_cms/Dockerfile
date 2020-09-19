FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY ./target/service_cms.jar service_cms.jar
ENTRYPOINT ["java","-jar","/service_cms.jar", "&"]