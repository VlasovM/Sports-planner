package ru.javlasov.clinic.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import ru.javlasov.clinic.enums.Specialization;

@Entity
@Table(name = "doctors")
@Getter
@Setter
public class Doctor {

    @Id
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "surname", nullable = false)
    private String surname;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "specialization", nullable = false)
    private Specialization specialization;

}
