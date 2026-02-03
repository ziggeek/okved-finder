FROM amazoncorretto:17

WORKDIR /app
COPY target/okved-finder.jar /app/okved-finder.jar
EXPOSE 8080


ENTRYPOINT ["java", "-jar", "okved-finder.jar"]