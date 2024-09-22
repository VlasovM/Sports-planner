package ru.javlasov.clinic.repository;

import org.springframework.data.repository.CrudRepository;
import ru.javlasov.clinic.model.HealthInformation;

public interface HealthInformationRepository extends CrudRepository<HealthInformation, Long> {

}
