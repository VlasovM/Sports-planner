package ru.javlasov.sportsplanner.repository;

import org.springframework.data.repository.ListCrudRepository;
import ru.javlasov.sportsplanner.model.Train;

import java.util.List;

public interface TrainRepository extends ListCrudRepository<Train, Long> {

    List<Train> findAllByUser(Long userId);

}
