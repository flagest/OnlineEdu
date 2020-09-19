FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY ./target/service_order.jar service_order.jar
ENTRYPOINT ["java","-jar","/service_order.jar", "&"]