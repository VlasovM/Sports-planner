package ru.javlasov.sportsplanner.repository;

import org.springframework.data.repository.ListCrudRepository;
import ru.javlasov.sportsplanner.model.ArticleStatus;

public interface ArticleStatusRepository extends ListCrudRepository<ArticleStatus, Long> {
}
