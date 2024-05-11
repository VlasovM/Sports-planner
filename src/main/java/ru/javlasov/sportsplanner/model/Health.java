package ru.javlasov.sportsplanner.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Table(name = "health")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Health {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "clinic", nullable = false)
    private String clinic;

    @Column(name = "doctor_specialization", nullable = false)
    private String doctorSpecialization;

    @Column(name = "doctor_full_name", nullable = false)
    private String doctorFullName;

    @Column(name = "result", nullable = false)
    private String result;

    @Column(name = "user_id", nullable = false)
    private Long user;

}
