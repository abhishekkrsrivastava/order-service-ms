FROM openjdk:jdk
WORKDIR /appcontainer
EXPOSE 9196
COPY ./target/order-service-ms.jar /appcontainer
ADD ./target/order-service-ms.jar order-service-ms.jar
ENTRYPOINT ["java","-jar","order-service-ms.jar"]