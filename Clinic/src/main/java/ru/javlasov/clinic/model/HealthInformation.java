package ru.javlasov.clinic.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "health_information")
@Getter
@Setter
@NamedEntityGraph(name = "healthInformation-entity-graph")
public class HealthInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "request_id", nullable = false, unique = true)
    private String requestId;

    @Column(name = "patient_name", nullable = false)
    private String patientName;

    @Column(name = "patient_middle_name")
    private String patientMiddleName;

    @Column(name = "patient_surname", nullable = false)
    private String patientSurname;

    @Column(name = "patient_birthday", nullable = false)
    private LocalDate patientBirthday;

    @Column(name = "doctor_id")
    private Long doctorId;

    @Column(name = "visited", nullable = false)
    private LocalDate visited;

    @Column(name = "result", nullable = false)
    private String result;

}
