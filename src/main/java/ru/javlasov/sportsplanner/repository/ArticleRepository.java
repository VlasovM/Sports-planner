package ru.javlasov.sportsplanner.repository;

import org.springframework.data.repository.ListCrudRepository;
import ru.javlasov.sportsplanner.model.Article;

import java.util.List;

public interface ArticleRepository extends ListCrudRepository<Article, Long> {

    List<Article> findAllByUser(Long userId);

    List<Article> findAllByStatusId(Long statusId);

}
