FROM amazoncorretto:11.0.13-alpine3.14

EXPOSE 9000

WORKDIR /app

COPY build/libs/App.jar .

CMD ["java", "-jar", "App.jar"]
