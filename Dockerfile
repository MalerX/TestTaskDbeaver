#
# Build stage
#
FROM maven:3.8.4-openjdk-17-slim AS build
RUN mkdir -p /build
WORKDIR /build
COPY pom.xml /build
RUN mkdir -p /build
#RUN mvn -B dependency:resolve dependency:resolve-plugins
COPY src /build/src
RUN mvn -Dmaven.test.skip=true -f /build/pom.xml clean package

#
# Package stage
#
FROM openjdk:17
COPY --from=build /build/target/WeatherHistory-0.0.1-SNAPSHOT.jar /usr/local/lib/app.jar
ENTRYPOINT ["java","-jar","/usr/local/lib/app.jar"]
