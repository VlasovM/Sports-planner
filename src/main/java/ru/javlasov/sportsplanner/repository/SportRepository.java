package ru.javlasov.sportsplanner.repository;

import org.springframework.data.repository.ListCrudRepository;
import ru.javlasov.sportsplanner.model.Sport;

public interface SportRepository extends ListCrudRepository<Sport, Long> {
}
