package ru.javlasov.sportsplanner.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.ListCrudRepository;
import ru.javlasov.sportsplanner.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends ListCrudRepository<User, Long> {

    @EntityGraph(value = "trains-tournaments-checkUp-articles-entity-graph")
    Optional<User> findById(Long id);

    @EntityGraph(value = "trains-tournaments-checkUp-articles-entity-graph")
    List<User> findAll();

}
