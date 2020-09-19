FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY ./target/service_ucenter.jar service_ucenter.jar
ENTRYPOINT ["java","-jar","/service_ucenter.jar", "&"]