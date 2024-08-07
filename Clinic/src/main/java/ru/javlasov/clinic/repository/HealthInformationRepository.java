package ru.javlasov.clinic.repository;

import org.springframework.data.repository.CrudRepository;
import ru.javlasov.clinic.model.HealthInformation;

import java.util.List;

public interface HealthInformationRepository extends CrudRepository<HealthInformation, Long> {

}
