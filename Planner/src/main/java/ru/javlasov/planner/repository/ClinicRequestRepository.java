package ru.javlasov.planner.repository;

import org.springframework.data.repository.CrudRepository;
import ru.javlasov.planner.model.ClinicRequest;

import java.util.List;
import java.util.Optional;

public interface ClinicRequestRepository extends CrudRepository<ClinicRequest, Long> {

    List<ClinicRequest> findAllByStatus(Long status);

    Optional<ClinicRequest> findByRequestId(String requestId);

}
