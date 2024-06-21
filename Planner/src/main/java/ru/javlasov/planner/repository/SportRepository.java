package ru.javlasov.planner.repository;

import org.springframework.data.repository.ListCrudRepository;
import ru.javlasov.planner.model.Sport;

public interface SportRepository extends ListCrudRepository<Sport, Long> {
}
