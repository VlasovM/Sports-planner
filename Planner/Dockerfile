FROM openjdk:17
WORKDIR /app/planner
COPY Planner/target/Planner-0.0.1.jar /app/planner/planner.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "planner.jar"]