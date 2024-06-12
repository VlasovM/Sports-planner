package ru.javlasov.sportsplanner.controller.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.javlasov.sportsplanner.dto.ArticleDto;
import ru.javlasov.sportsplanner.service.ArticleService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/articles")
public class ArticleRestController {

    private final ArticleService articleService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ArticleDto> getAllArticles() {
        return articleService.getAllArticles();
    }

    @GetMapping("/article/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ArticleDto getArticleById(@PathVariable("id") Long id) {
        return articleService.getArticleById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createArticle(@Valid @RequestBody ArticleDto articleDto) {
        articleService.createArticle(articleDto);
    }

    @PatchMapping()
    @ResponseStatus(HttpStatus.OK)
    public void updateArticle(@Valid @RequestBody ArticleDto articleDto) {
        articleService.editArticle(articleDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteArticleById(@PathVariable("id") Long id) {
        articleService.deleteArticle(id);
    }

    @PatchMapping("/accept/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void acceptArticle(@PathVariable("id") Long id) {
        articleService.acceptArticle(id);
    }

    @PatchMapping("/decline/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void declineArticle(@PathVariable("id") Long id) {
        articleService.declineArticle(id);
    }

    @GetMapping("/user/")
    @ResponseStatus(HttpStatus.OK)
    public List<ArticleDto> getMyArticles() {
        return articleService.getAllArticlesByCurrentUser();
    }

    @GetMapping("/validate")
    @ResponseStatus(HttpStatus.OK)
    public List<ArticleDto> getArticlesForValidate() {
        return articleService.getArticleForValidate();
    }

}
