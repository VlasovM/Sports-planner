package ru.javlasov.planner.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import ru.javlasov.planner.enums.Status;

import java.time.LocalDate;

@Entity
@Table(name = "clinic_request")
@Getter
@Setter
public class ClinicRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "request_id", nullable = false, unique = true)
    private String requestId;

    @Column(name = "status", nullable = false)
    private Status status;

    @Column(name = "note")
    private String note;

    @Column(name = "doctor_full_name")
    private String doctorFullName;

    @Column(name = "doctor_specialization")
    private String doctorSpecialization;

    @Column(name = "clinic")
    private String clinic;

    @Column(name = "date_visited")
    private LocalDate dateVisited;

    @Column(name = "patient_name")
    private String patientName;

    @Column(name = "patient_surname")
    private String patientSurname;

    @Column(name = "patient_iddle_name")
    private String patientMiddleName;

    @Column(name = "patient_birthday")
    private LocalDate patientBirthday;

    @Column(name = "result")
    private String result;

    @Column(name = "user_id")
    private Long userId;

}
