package ru.javlasov.sportsplanner.repository;

import org.springframework.data.repository.ListCrudRepository;
import ru.javlasov.sportsplanner.model.Train;

public interface TrainRepository extends ListCrudRepository<Train, Long> {
}
