package ru.javlasov.sportsplanner.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.javlasov.sportsplanner.dto.ArticleDto;
import ru.javlasov.sportsplanner.service.ArticleService;
import ru.javlasov.sportsplanner.service.UserCredentialsService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/articles")
public class ArticleController {

    private final ArticleService articleService;

    private final UserCredentialsService userCredentialsService;

    @GetMapping
    public String articles() {
        return "articles";
    }

    @GetMapping("/article/{id}")
    public String article(@PathVariable(name = "id") Long id, Model model) {
        model.addAttribute("id", id);
        return "article";
    }

    @GetMapping("/create")
    public String create() {
        return "createArticle";
    }

    @GetMapping("/edit/{id}")
    public String editHealth(@PathVariable(name = "id") Long id, Model model) {
        ArticleDto articleDto = articleService.getArticleById(id);
        model.addAttribute("id", id);
        model.addAttribute("text", articleDto.getText());
        model.addAttribute("title", articleDto.getTitle());
        model.addAttribute("created", articleDto.getCreated());
        return "editArticle";
    }

    @GetMapping("/user/")
    public String articlesByUser(Model model) {
        var currentUser = userCredentialsService.getCurrentAuthUser();
        model.addAttribute("userId", currentUser.getUser().getId());
        return "articlesByUser";
    }


}
