FROM gradle:8.5-jdk21 AS build
WORKDIR /app
COPY server/market-place-common server/market-place-common
COPY server/currency-service server/currency-service
WORKDIR /app/server/currency-service
RUN gradle build -x test --no-daemon

FROM amazoncorretto:21
WORKDIR /app
COPY --from=build /app/server/currency-service/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
