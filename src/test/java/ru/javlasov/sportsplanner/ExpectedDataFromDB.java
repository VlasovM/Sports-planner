package ru.javlasov.sportsplanner;

import ru.javlasov.sportsplanner.dto.ArticleDto;
import ru.javlasov.sportsplanner.dto.ArticleStatusDto;
import ru.javlasov.sportsplanner.dto.HealthDto;
import ru.javlasov.sportsplanner.dto.SportDto;
import ru.javlasov.sportsplanner.dto.TournamentDto;
import ru.javlasov.sportsplanner.dto.TrainDto;
import ru.javlasov.sportsplanner.dto.UserDto;
import ru.javlasov.sportsplanner.model.Article;
import ru.javlasov.sportsplanner.model.ArticleStatus;
import ru.javlasov.sportsplanner.model.Health;
import ru.javlasov.sportsplanner.model.Sport;
import ru.javlasov.sportsplanner.model.Tournament;
import ru.javlasov.sportsplanner.model.Train;
import ru.javlasov.sportsplanner.model.User;
import ru.javlasov.sportsplanner.model.UserCredentials;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class ExpectedDataFromDB {

    public static List<ArticleStatus> getExpectedArticlesStatusFromDB() {
        var status1 = new ArticleStatus(1L, "article_status1");
        var status2 = new ArticleStatus(2L, "article_status2");
        return List.of(status1, status2);
    }

    public static List<Article> getExpectedArticlesFromDB() {
        var article1 = new Article(1L, getExpectedArticlesStatusFromDB().get(0), "title1",
                "text1", LocalDate.of(2000, 1, 1), 1L);
        var article2 = new Article(2L, getExpectedArticlesStatusFromDB().get(1), "title2",
                "text2", LocalDate.of(2000, 1, 1), 1L);
        return List.of(article1, article2);
    }

    public static List<User> getExpectedUsersFromDB() {
        var user1 = new User(1L, "name1", null, "surname1", 22,
                LocalDateTime.of(2000, 1, 1, 1, 1), "biography1",
                getExpectedSportsFromDB().get(0), getExpectedArticlesFromDB(), getExpectedTrainsFromDB(),
                getExpectedTournamentsFromDB(), getExpectedHealthFromDB());
        var user2 = new User(2L, "name2", "middlename2", "surname2", 25,
                LocalDateTime.of(2000, 1, 1, 1, 1), "biography2",
                getExpectedSportsFromDB().get(1), null, null, null, null);
        return List.of(user1, user2);
    }

    public static List<Sport> getExpectedSportsFromDB() {
        var sport1 = new Sport(1L, "sport1");
        var sport2 = new Sport(2L, "sport2");
        return List.of(sport1, sport2);
    }

    public static List<Health> getExpectedHealthFromDB() {
        var health1 = new Health(1L, LocalDate.of(2000, 1, 1), "clinic1",
                "doctor_specialization1", "doctor_full_name1",
                "result1", 1L);
        var health2 = new Health(2L, LocalDate.of(2000, 1, 1), "clinic2",
                "doctor_specialization2", "doctor_full_name2",
                "result2", 1L);
        return List.of(health1, health2);
    }

    public static List<Tournament> getExpectedTournamentsFromDB() {
        var tournament1 = new Tournament(1L, LocalDate.of(2000, 1, 1),
                "title1", "opponent1", "result1", "reflection1", 1L);
        var tournament2 = new Tournament(2L, LocalDate.of(2000, 1, 1), "title2",
                "opponent2", "result2", null, 1L);
        return List.of(tournament1, tournament2);
    }

    public static List<Train> getExpectedTrainsFromDB() {
        var train1 = new Train(1L, LocalDateTime.of(2000, 1, 1, 1, 1),
                "description1", "reflection1", 1L);
        var train2 = new Train(2L, LocalDateTime.of(2000, 1, 1, 1, 1),
                "description2", null, 1L);
        return List.of(train1, train2);
    }

    public static List<ArticleDto> getExpectedArticleDtoFromDB() {
        var articleDto = new ArticleDto(1L, ArticleStatusDto.VERIFICATION, "title1", "text1",
                LocalDate.of(2000, 1, 1), 1L);
        return List.of(articleDto);
    }

    public static List<UserCredentials> getExpectedUserCredentialsFromDB() {
        var userCredentials = new UserCredentials(1L, "test@mail.ru", "password",
                getExpectedUsersFromDB().get(0));
        return List.of(userCredentials);
    }

    public static List<UserDto> getExpectedUserDtoFromDB() {
        var expectedUser = getExpectedUserCredentialsFromDB().get(0);
        var expectedUserDto = new UserDto();
        expectedUserDto.setId(expectedUser.getId());
        expectedUserDto.setSport(new SportDto(expectedUser.getUser().getSport().getId(),
                expectedUser.getUser().getSport().getTitle()));
        expectedUserDto.setName(expectedUser.getUser().getName());
        expectedUserDto.setSurname(expectedUser.getUser().getSurname());
        expectedUserDto.setMiddleName(expectedUser.getUser().getMiddleName());
        expectedUserDto.setBirthday(expectedUser.getUser().getBirthday());
        expectedUserDto.setEmail(expectedUser.getEmail());
        expectedUserDto.setBiography(expectedUser.getUser().getBiography());
        expectedUserDto.setAge(expectedUser.getUser().getAge());
        expectedUserDto.setPassword(expectedUser.getPassword());
        return List.of(expectedUserDto);
    }

    public static List<TrainDto> getExpectedTrainDtoFromDB() {
        var expectedTrainFirst = getExpectedTrainsFromDB().get(0);
        var expectedTrainDtoFirst = new TrainDto();
        expectedTrainDtoFirst.setId(expectedTrainFirst.getId());
        expectedTrainDtoFirst.setUser(expectedTrainFirst.getUser());
        expectedTrainDtoFirst.setDate(expectedTrainFirst.getDate());
        expectedTrainDtoFirst.setTitle(expectedTrainFirst.getTitle());
        expectedTrainDtoFirst.setReflection(expectedTrainFirst.getReflection());

        var expectedTrainSecond = getExpectedTrainsFromDB().get(1);
        var expectedTrainDtoSecond = new TrainDto();
        expectedTrainDtoSecond.setId(expectedTrainSecond.getId());
        expectedTrainDtoSecond.setUser(expectedTrainSecond.getUser());
        expectedTrainDtoSecond.setDate(expectedTrainSecond.getDate());
        expectedTrainDtoSecond.setTitle(expectedTrainSecond.getTitle());
        expectedTrainDtoSecond.setReflection(expectedTrainSecond.getReflection());

        return List.of(expectedTrainDtoFirst, expectedTrainDtoSecond);
    }

    public static List<HealthDto> getExpectedHealthDto() {
        var expectedHealthFirst = getExpectedHealthFromDB().get(0);
        var expectedHealthDtoFirst = new HealthDto();
        expectedHealthDtoFirst.setId(expectedHealthFirst.getId());
        expectedHealthDtoFirst.setDate(expectedHealthFirst.getDate());
        expectedHealthDtoFirst.setClinic(expectedHealthFirst.getClinic());
        expectedHealthDtoFirst.setUser(expectedHealthFirst.getUser());
        expectedHealthDtoFirst.setResult(expectedHealthFirst.getResult());
        expectedHealthDtoFirst.setDoctorFullName(expectedHealthFirst.getDoctorFullName());
        expectedHealthDtoFirst.setDoctorSpecialization(expectedHealthFirst.getDoctorSpecialization());

        var expectedHealthSecond = getExpectedHealthFromDB().get(0);
        var expectedHealthDtoSecond = new HealthDto();
        expectedHealthDtoSecond.setId(expectedHealthSecond.getId());
        expectedHealthDtoSecond.setDate(expectedHealthSecond.getDate());
        expectedHealthDtoSecond.setClinic(expectedHealthSecond.getClinic());
        expectedHealthDtoSecond.setUser(expectedHealthSecond.getUser());
        expectedHealthDtoSecond.setResult(expectedHealthSecond.getResult());
        expectedHealthDtoSecond.setDoctorFullName(expectedHealthSecond.getDoctorFullName());
        expectedHealthDtoSecond.setDoctorSpecialization(expectedHealthSecond.getDoctorSpecialization());

        return List.of(expectedHealthDtoFirst, expectedHealthDtoSecond);
    }

    public static List<TournamentDto> getExpectedTournamentDto() {
        var expectedTournamentFirst = getExpectedTournamentsFromDB().get(0);
        var expectedTournamentDtoFirst = new TournamentDto();
        expectedTournamentDtoFirst.setId(expectedTournamentFirst.getId());
        expectedTournamentDtoFirst.setUser(expectedTournamentFirst.getUser());
        expectedTournamentDtoFirst.setResult(expectedTournamentFirst.getResult());
        expectedTournamentDtoFirst.setDate(expectedTournamentFirst.getDate());
        expectedTournamentDtoFirst.setReflection(expectedTournamentFirst.getReflection());
        expectedTournamentDtoFirst.setTitle(expectedTournamentFirst.getTitle());
        expectedTournamentDtoFirst.setOpponent(expectedTournamentFirst.getOpponent());

        var expectedTournamentSecond = getExpectedTournamentsFromDB().get(1);
        var expectedTournamentDtoSecond = new TournamentDto();
        expectedTournamentDtoSecond.setId(expectedTournamentSecond.getId());
        expectedTournamentDtoSecond.setUser(expectedTournamentSecond.getUser());
        expectedTournamentDtoSecond.setResult(expectedTournamentSecond.getResult());
        expectedTournamentDtoSecond.setDate(expectedTournamentSecond.getDate());
        expectedTournamentDtoSecond.setReflection(expectedTournamentSecond.getReflection());
        expectedTournamentDtoSecond.setTitle(expectedTournamentSecond.getTitle());
        expectedTournamentDtoSecond.setOpponent(expectedTournamentSecond.getOpponent());
        return List.of(expectedTournamentDtoFirst, expectedTournamentDtoSecond);
    }

}
