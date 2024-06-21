package ru.javlasov.planner.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.lang.NonNull;
import ru.javlasov.planner.model.Article;
import ru.javlasov.planner.model.User;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends ListCrudRepository<Article, Long> {

    @NonNull
    @EntityGraph(value = "article-entity-graph")
    List<Article> findAllByUser(User user);

    @NonNull
    @EntityGraph(value = "article-entity-graph")
    List<Article> findAllByStatusId(Long statusId);

    @NonNull
    @EntityGraph(value = "article-entity-graph")
    Optional<Article> findById(@NonNull Long id);

}
