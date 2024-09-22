FROM openjdk:17
WORKDIR /app/clinic
COPY Clinic/target/Clinic-0.0.1.jar /app/clinic/clinic.jar
EXPOSE 8091
ENTRYPOINT ["java", "-jar", "clinic.jar"]