package ru.javlasov.planner.repository;

import org.springframework.data.repository.CrudRepository;
import ru.javlasov.planner.enums.Status;
import ru.javlasov.planner.model.ClinicRequest;

import java.util.List;

public interface ClinicRequestRepository extends CrudRepository<ClinicRequest, Long> {

    List<ClinicRequest> findAllByStatus(Status status);

}
