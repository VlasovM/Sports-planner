package ru.javlasov.sportsplanner.repository;

import org.springframework.data.repository.ListCrudRepository;
import ru.javlasov.sportsplanner.model.Tournament;

import java.util.List;

public interface TournamentRepository extends ListCrudRepository<Tournament, Long> {

    List<Tournament> findAllByUser(Long id);

}
