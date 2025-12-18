FROM gradle:jdk17-corretto AS build

WORKDIR /data

COPY build.gradle settings.gradle gradlew ./
COPY gradle gradle

RUN --mount=type=cache,target=/home/gradle/.gradle gradle dependencies --no-daemon

COPY src src

RUN --mount=type=cache,target=/home/gradle/.gradle gradle bootJar --no-daemon -x test

FROM amazoncorretto:21-alpine

VOLUME /data

ARG JAR_FILE=geomap-0.0.1-SNAPSHOT.jar

WORKDIR /application

COPY --from=build /data/build/libs/${JAR_FILE} application.jar

ENTRYPOINT exec java -jar application.jar ${0} ${@}
