FROM openjdk:17
WORKDIR /app/journaling
COPY Journaling/target/Journaling-0.0.1.jar /app/journaling/journaling.jar
EXPOSE 8090
ENTRYPOINT ["java", "-jar", "journaling.jar"]