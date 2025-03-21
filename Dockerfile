FROM bellsoft/liberica-openjdk-alpine:21
WORKDIR /app
COPY ./build/libs/order-service.jar /app/order-service.jar
ENTRYPOINT ["java", "-jar", "order-service.jar"]