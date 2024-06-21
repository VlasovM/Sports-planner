package ru.javlasov.planner.repository;

import org.springframework.data.repository.ListCrudRepository;
import ru.javlasov.planner.model.ArticleStatus;

public interface ArticleStatusRepository extends ListCrudRepository<ArticleStatus, Long> {
}
