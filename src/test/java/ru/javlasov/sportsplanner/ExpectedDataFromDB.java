package ru.javlasov.sportsplanner;

import ru.javlasov.sportsplanner.dto.ArticleDto;
import ru.javlasov.sportsplanner.model.Article;
import ru.javlasov.sportsplanner.model.ArticleStatus;
import ru.javlasov.sportsplanner.model.Health;
import ru.javlasov.sportsplanner.model.Sport;
import ru.javlasov.sportsplanner.model.Tournament;
import ru.javlasov.sportsplanner.model.Train;
import ru.javlasov.sportsplanner.model.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class ExpectedDataFromDB {

    public static List<ArticleStatus> getExpectedArticlesStatusFromDB() {
        var status1 = new ArticleStatus(1L, "На проверке модератором");
        var status2 = new ArticleStatus(2L, "Опубликовано");
        return List.of(status1, status2);
    }

    public static List<Article> getExpectedArticlesFromDB() {
        var article1 = new Article(1L, getExpectedArticlesStatusFromDB().get(0), "Статья1",
                "Текст1", LocalDate.of(2000, 1, 1), 1L);
        var article2 = new Article(2L, getExpectedArticlesStatusFromDB().get(1), "Статья2",
                "Текст2", LocalDate.of(2000, 1, 1), 1L);
        return List.of(article1, article2);
    }

    public static List<User> getExpectedUsersFromDB() {
        var user1 = new User(1L, "Имя1", null, "Фамилия1", 22,
                LocalDateTime.of(2000, 1, 1, 1, 1), "Биография1",
                getExpectedSportsFromDB().get(0), getExpectedArticlesFromDB(), getExpectedTrainsFromDB(),
                getExpectedTournamentsFromDB(), getExpectedHealthFromDB());
        var user2 = new User(2L, "Имя2", "Отчество2", "Фамилия2", 25,
                LocalDateTime.of(2000, 1, 1, 1, 1), "Биография2",
                getExpectedSportsFromDB().get(1), null, null, null, null);
        return List.of(user1, user2);
    }

    public static List<Sport> getExpectedSportsFromDB() {
        var sport1 = new Sport(1L, "Спорт1");
        var sport2 = new Sport(2L, "Спорт2");
        return List.of(sport1, sport2);
    }

    public static List<Health> getExpectedHealthFromDB() {
        var health1 = new Health(1L, LocalDate.of(2000, 1, 1), "Клиника1",
                "Доктор1", "ФИО1", "Результат1", 1L);
        var health2 = new Health(2L, LocalDate.of(2000, 1, 1), "Клиника2",
                "Доктор2", "ФИО2", "Результат2", 1L);
        return List.of(health1, health2);
    }

    public static List<Tournament> getExpectedTournamentsFromDB() {
        var tournament1 = new Tournament(1L, LocalDate.of(2000, 1, 1),
                "Заголовок1", "Соперник1", "Результат1", "Рефлексия1", 1L);
        var tournament2 = new Tournament(2L, LocalDate.of(2000, 1, 1), "Заголовок2",
                "Соперник2", "Результат2", null, 1L);
        return List.of(tournament1, tournament2);
    }

    public static List<Train> getExpectedTrainsFromDB() {
        var train1 = new Train(1L, LocalDateTime.of(2000, 1, 1, 1, 1),
                "Описание1", "Рефлексия1", 1L);
        var train2 = new Train(2L, LocalDateTime.of(2000, 1, 1, 1, 1),
                "Описание2", null, 1L);
        return List.of(train1, train2);
    }

    public static List<ArticleDto> getExpectedArticleDtoFromDB() {
        var articleDto = new ArticleDto(1L, ArticleStatusDto.VERIFICATION, "Статья1", "Текст1",
                LocalDate.of(2000, 1, 1), 1L);
        return List.of(articleDto);
    }

}
