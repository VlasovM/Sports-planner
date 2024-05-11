package ru.javlasov.sportsplanner.repository;

import org.springframework.data.repository.ListCrudRepository;
import ru.javlasov.sportsplanner.model.Article;

public interface ArticleRepository extends ListCrudRepository<Article, Long> {
}
