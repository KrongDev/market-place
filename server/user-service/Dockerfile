FROM gradle:8.5-jdk21 AS build
WORKDIR /app
COPY server/market-place-common server/market-place-common
COPY server/user-service server/user-service
WORKDIR /app/server/user-service
RUN gradle build -x test --no-daemon

FROM amazoncorretto:21
WORKDIR /app
COPY --from=build /app/server/user-service/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
