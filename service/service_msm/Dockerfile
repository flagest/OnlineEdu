FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY ./target/service_msm.jar service_msm.jar
ENTRYPOINT ["java","-jar","/service_msm.jar", "&"]