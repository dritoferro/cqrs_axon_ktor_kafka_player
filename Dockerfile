FROM adoptopenjdk/openjdk11:jre-11.0.16.1_1-alpine

WORKDIR /app

COPY ./build/libs/player.jar .

EXPOSE 8080

ENTRYPOINT java -jar player.jar
