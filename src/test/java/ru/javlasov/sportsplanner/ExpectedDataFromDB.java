package ru.javlasov.sportsplanner;

import ru.javlasov.sportsplanner.dto.ArticleDto;
import ru.javlasov.sportsplanner.dto.HealthDto;
import ru.javlasov.sportsplanner.dto.TournamentDto;
import ru.javlasov.sportsplanner.dto.UserDto;
import ru.javlasov.sportsplanner.dto.WorkoutDto;
import ru.javlasov.sportsplanner.enums.ArticleStatusDto;
import ru.javlasov.sportsplanner.model.Article;
import ru.javlasov.sportsplanner.model.ArticleStatus;
import ru.javlasov.sportsplanner.model.Health;
import ru.javlasov.sportsplanner.model.Role;
import ru.javlasov.sportsplanner.model.Sport;
import ru.javlasov.sportsplanner.model.Tournament;
import ru.javlasov.sportsplanner.model.User;
import ru.javlasov.sportsplanner.model.UserCredentials;
import ru.javlasov.sportsplanner.model.Workout;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ExpectedDataFromDB {

    public static List<Sport> getExpectedSportsFromDB() {
        var sport1 = new Sport(1L, "sport1");
        var sport2 = new Sport(2L, "sport2");
        return List.of(sport1, sport2);
    }

    public static List<User> getExpectedUsersFromDB() {
        var user1 = new User(1L, "name1", null, "surname1", 22,
                LocalDate.of(2000, 1, 1), "biography1",
                getExpectedSportsFromDB().get(0).getId());
        var user2 = new User(2L, "name2", "middlename2", "surname2", 25,
                LocalDate.of(2000, 1, 1), "biography2",
                getExpectedSportsFromDB().get(1).getId());
        return List.of(user1, user2);
    }

    public static List<Role> getExpectedRolesFromDB() {
        var roleUser = new Role(1L, "USER");
        var roleAdmin = new Role(2L, "ADMIN");
        return List.of(roleUser, roleAdmin);
    }

    public static List<UserCredentials> getExpectedUserCredentialsFromDB() {
        var userCredentials1 = new UserCredentials(1L, "name1@mail.ru", "password",
                getExpectedUsersFromDB().get(0), getExpectedRolesFromDB().get(0));
        var userCredentials2 = new UserCredentials(1L, "name2@mail.ru", "password",
                getExpectedUsersFromDB().get(1), getExpectedRolesFromDB().get(0));
        return List.of(userCredentials1, userCredentials2);
    }

    public static List<Article> getExpectedArticlesFromDB() {
        var article1 = new Article(1L, getExpectedArticlesStatusFromDB().get(1), "title1",
                "text1", LocalDate.of(2000, 1, 1), getExpectedUsersFromDB().get(0));
        var article2 = new Article(2L, getExpectedArticlesStatusFromDB().get(2), "title2",
                "text2", LocalDate.of(2000, 1, 1), getExpectedUsersFromDB().get(0));
        var article3 = new Article(3L, getExpectedArticlesStatusFromDB().get(3), "title3",
                "text3", LocalDate.of(2000, 1, 1), getExpectedUsersFromDB().get(0));
        return List.of(article1, article2, article3);
    }

    public static List<ArticleStatus> getExpectedArticlesStatusFromDB() {
        var unknown = new ArticleStatus(1L, "unknown");
        var verification = new ArticleStatus(2L, "verification");
        var published = new ArticleStatus(3L, "published");
        var decline = new ArticleStatus(4L, "decline");
        return List.of(unknown, verification, published, decline);
    }

    public static List<Workout> getExpectedWorkoutFromDB() {
        var train1 = new Workout(1L, LocalDateTime.of(2000, 1, 1, 1, 1),
                "description1", "reflection1", getExpectedUsersFromDB().get(0));
        var train2 = new Workout(2L, LocalDateTime.of(2000, 1, 1, 1, 1),
                "description2", null, getExpectedUsersFromDB().get(0));
        return List.of(train1, train2);
    }

    public static List<Health> getExpectedHealthFromDB() {
        var health1 = new Health(1L, LocalDate.of(2000, 1, 1), "clinic1",
                "doctor_specialization1", "doctor_full_name1",
                "result1", getExpectedUsersFromDB().get(0));
        var health2 = new Health(2L, LocalDate.of(2000, 1, 1), "clinic2",
                "doctor_specialization2", "doctor_full_name2",
                "result2", getExpectedUsersFromDB().get(0));
        return List.of(health1, health2);
    }

    public static List<Tournament> getExpectedTournamentsFromDB() {
        var tournament1 = new Tournament(1L, LocalDate.of(2000, 1, 1),
                "title1", "opponent1", "result1", "reflection1",
                getExpectedUsersFromDB().get(0));
        var tournament2 = new Tournament(2L, LocalDate.of(2000, 1, 1), "title2",
                "opponent2", "result2", null, getExpectedUsersFromDB().get(0));
        return List.of(tournament1, tournament2);
    }

    public static List<UserDto> getExpectedUsersDtoFromDB() {
        var user1 = getExpectedUsersFromDB().get(0);
        var userCredentials1 = getExpectedUserCredentialsFromDB().get(0);
        var userDto1 = new UserDto(user1.getId(), user1.getName(), user1.getSurname(), user1.getMiddleName(),
                user1.getAge(), user1.getBirthday(), user1.getBiography(), userCredentials1.getEmail(),
                userCredentials1.getPassword(), user1.getSport());

        var user2 = getExpectedUsersFromDB().get(1);
        var userCredentials2 = getExpectedUserCredentialsFromDB().get(1);
        var userDto2 = new UserDto(user2.getId(), user2.getName(), user2.getSurname(), user2.getMiddleName(),
                user2.getAge(), user2.getBirthday(), user2.getBiography(), userCredentials2.getEmail(),
                userCredentials2.getPassword(), user2.getSport());

        return List.of(userDto1, userDto2);
    }

    public static List<ArticleDto> getExpectedArticlesDtoFromDB() {
        var userDto = getExpectedUsersDtoFromDB().get(0);

        var article1 = getExpectedArticlesFromDB().get(0);
        var articleDto1 = new ArticleDto(article1.getId(), ArticleStatusDto.getById(article1.getStatus().getId()),
                article1.getTitle(), article1.getText(), article1.getCreated(), userDto);


        var article2 = getExpectedArticlesFromDB().get(1);
        var articleDto2 = new ArticleDto(article2.getId(), ArticleStatusDto.getById(article2.getStatus().getId()),
                article2.getTitle(), article2.getText(), article2.getCreated(), userDto);

        var article3 = getExpectedArticlesFromDB().get(0);
        var articleDto3 = new ArticleDto(article3.getId(), ArticleStatusDto.getById(article3.getStatus().getId()),
                article3.getTitle(), article3.getText(), article3.getCreated(), userDto);

        return List.of(articleDto1, articleDto2, articleDto3);
    }

    public static List<WorkoutDto> getExpectedWorkoutDtoFromDB() {
        var userDto = getExpectedUsersDtoFromDB().get(0);

        var workout1 = getExpectedWorkoutFromDB().get(0);
        var workoutDto1 = new WorkoutDto(workout1.getId(), workout1.getDate(), workout1.getTitle(),
                workout1.getReflection(), userDto);

        var workout2 = getExpectedWorkoutFromDB().get(0);
        var workoutDto2 = new WorkoutDto(workout2.getId(), workout2.getDate(), workout2.getTitle(),
                workout2.getReflection(), userDto);

        return List.of(workoutDto1, workoutDto2);
    }

    public static List<HealthDto> getExpectedHealthDtoFromDB() {
        var userDto = getExpectedUsersDtoFromDB().get(0);

        var health1 = getExpectedHealthFromDB().get(0);
        var healthDto1 = new HealthDto(health1.getId(), health1.getDate(), health1.getClinic(),
                health1.getDoctorSpecialization(), health1.getDoctorFullName(), health1.getResult(), userDto);


        var health2 = getExpectedHealthFromDB().get(1);
        var healthDto2 = new HealthDto(health2.getId(), health2.getDate(), health2.getClinic(),
                health2.getDoctorSpecialization(), health2.getDoctorFullName(), health2.getResult(), userDto);

        return List.of(healthDto1, healthDto2);
    }

    public static List<TournamentDto> getExpectedTournamentsDtoFromDB() {
        var userDto = getExpectedUsersDtoFromDB().get(0);

        var tournament1 = getExpectedTournamentsFromDB().get(0);
        var tournamentDto1 = new TournamentDto(tournament1.getId(), tournament1.getDate(),
                tournament1.getTitle(), tournament1.getOpponent(), tournament1.getResult(), tournament1.getReflection(),
                userDto);

        var tournament2 = getExpectedTournamentsFromDB().get(1);
        var tournamentDto2 = new TournamentDto(tournament2.getId(), tournament2.getDate(),
                tournament2.getTitle(), tournament2.getOpponent(), tournament2.getResult(), tournament2.getReflection(),
                userDto);

        return List.of(tournamentDto1, tournamentDto2);
    }

}
