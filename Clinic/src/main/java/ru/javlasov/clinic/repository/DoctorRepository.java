package ru.javlasov.clinic.repository;

import org.springframework.data.repository.CrudRepository;
import ru.javlasov.clinic.model.Doctor;

import java.util.Optional;

public interface DoctorRepository extends CrudRepository<Doctor, Long> {

    Optional<Doctor> findDoctorByFullNameAndSpecializationId(String fullName, Long specializationId);

}
