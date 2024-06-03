package ru.javlasov.sportsplanner.repository;

import org.springframework.data.repository.ListCrudRepository;
import ru.javlasov.sportsplanner.model.Article;
import ru.javlasov.sportsplanner.model.ArticleStatus;

import java.util.List;

public interface ArticleRepository extends ListCrudRepository<Article, Long> {

    List<Article> findAllByUser(Long userId);

    List<Article> findAllByStatusId(Long statusId);

    List<Article> findAllByStatus(ArticleStatus articleStatus);

}
