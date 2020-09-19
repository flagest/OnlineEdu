FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY ./target/service_vod.jar service_vod.jar
ENTRYPOINT ["java","-jar","/service_vod.jar", "&"]