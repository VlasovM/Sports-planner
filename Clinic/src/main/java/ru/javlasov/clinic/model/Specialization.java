package ru.javlasov.clinic.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "specializations")
@Getter
@Setter
public class Specialization {

    @Id
    private Long id;

    @Column(name = "title", nullable = false, unique = true)
    private String specialization;

}
