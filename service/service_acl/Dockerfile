FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY ./target/service_acl.jar service_acl.jar
ENTRYPOINT ["java","-jar","/service_acl.jar", "&"]