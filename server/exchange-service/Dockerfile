FROM gradle:8.5-jdk21 AS build
WORKDIR /app
COPY server/market-place-common server/market-place-common
COPY server/exchange-service server/exchange-service
WORKDIR /app/server/exchange-service
RUN gradle build -x test --no-daemon

FROM amazoncorretto:21
WORKDIR /app
COPY --from=build /app/server/exchange-service/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
