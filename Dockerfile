FROM openjdk:24-slim-bullseye

WORKDIR /app

COPY target/candidate-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 2109

ENV SPRING_PROFILES_ACTIVE=prod

ENTRYPOINT ["java", "-jar", "app.jar"]
