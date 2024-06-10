package ru.javlasov.sportsplanner;

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
        var article1 = new Article(1L, getExpectedArticlesStatusFromDB().get(0), "title1",
                "text1", LocalDate.of(2000, 1, 1), getExpectedUsersFromDB().get(0));
        var article2 = new Article(2L, getExpectedArticlesStatusFromDB().get(1), "title2",
                "text2", LocalDate.of(2000, 1, 1), getExpectedUsersFromDB().get(0));
        var article3 = new Article(3L, getExpectedArticlesStatusFromDB().get(2), "title3",
                "text3", LocalDate.of(2000, 1, 1), getExpectedUsersFromDB().get(0));
        return List.of(article1, article2, article3);
    }

    public static List<ArticleStatus> getExpectedArticlesStatusFromDB() {
        var status1 = new ArticleStatus(1L, "article_status1");
        var status2 = new ArticleStatus(2L, "article_status2");
        var status3 = new ArticleStatus(3L, "article_status3");
        return List.of(status1, status2, status3);
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
//
//    public static Set<Tournament> getExpectedTournamentsSetFromDB() {
//        return new HashSet<>(getExpectedTournamentsListFromDB());
//    }
//
//    public static List<User> getExpectedUsersFromDB() {
//        var user1 = new User(1L, "name1", null, "surname1", 22,
//                LocalDate.of(2000, 1, 1), "biography1",
//                getExpectedSportsFromDB().iterator().next().getId(), getExpectedArticlesSetFromDB(),
//                getExpectedTrainsSetFromDB(), getExpectedTournamentsSetFromDB(), getExpectedHealthSetFromDB());
//        var user2 = new User(2L, "name2", "middlename2", "surname2", 25,
//                LocalDate.of(2000, 1, 1), "biography2",
//                2L, null, null, null, null);
//        return List.of(user1, user2);
//    }
//
//    public static List<ArticleDto> getExpectedArticleDtoFromDB() {
//        var user = getExpectedUsersFromDB().get(0);
//        var userFullName = user.getName() + " " + user.getMiddleName() + " " + user.getSurname();
//        var articleDto = new ArticleDto(1L, ArticleStatusEnum.UNKNOWN, "title1", "text1",
//                LocalDate.of(2000, 1, 1), 1L, userFullName);
//        return List.of(articleDto);
//    }
//
//    public static List<Role> getExpectedRolesFromDB() {
//        var roleFirst = new Role();
//        roleFirst.setId(1L);
//        roleFirst.setRole("USER");
//        var roleSecond = new Role();
//        roleSecond.setId(2L);
//        roleSecond.setRole("ADMIN");
//        return List.of(roleFirst, roleSecond);
//    }
//
//    public static List<UserCredentials> getExpectedUserCredentialsFromDB() {
//        var userCredentials = new UserCredentials(1L, "test@mail.ru", "password",
//                getExpectedUsersFromDB().get(0), getExpectedRolesFromDB().get(0));
//        return List.of(userCredentials);
//    }

//    public static List<UserDto> getExpectedUserDtoFromDB() {
//        var expectedUser = getExpectedUserCredentialsFromDB().get(0);
//        var expectedUserDto = new UserDto();
//        expectedUserDto.setId(expectedUser.getId());
//        expectedUserDto.setSport(ExpectedDataFromDB.getExpectedSportsFromDB().iterator().next().getId());
//        expectedUserDto.setName(expectedUser.getUser().getName());
//        expectedUserDto.setSurname(expectedUser.getUser().getSurname());
//        expectedUserDto.setMiddleName(expectedUser.getUser().getMiddleName());
//        expectedUserDto.setBirthday(expectedUser.getUser().getBirthday());
//        expectedUserDto.setEmail(expectedUser.getEmail());
//        expectedUserDto.setBiography(expectedUser.getUser().getBiography());
//        expectedUserDto.setAge(expectedUser.getUser().getAge());
//        expectedUserDto.setPassword(expectedUser.getPassword());
//        return List.of(expectedUserDto);
//    }
//
//    public static Set<TrainDto> getExpectedTrainDtoFromDB() {
//        var dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm", Locale.ENGLISH);
//        Set<Train> expectedTrains = getExpectedTrainsFromDB();
//        return expectedTrains.stream().map(entity -> {
//            var expectedDto = new TrainDto();
//            expectedDto.setId(entity.getId());
//            expectedDto.setUser(entity.getUser());
//            expectedDto.setDate(entity.getDate().format(dateTimeFormatter));
//            expectedDto.setTitle(entity.getTitle());
//            expectedDto.setReflection(entity.getReflection());
//            return expectedDto;
//        }).collect(Collectors.toSet());
//    }
//
//    public static Set<HealthDto> getExpectedHealthDto() {
//        Set<Health> expectedHealth = getExpectedHealthFromDB();
//        return expectedHealth.stream().map(entity -> {
//            var expectedDto = new HealthDto();
//            expectedDto.setId(entity.getId());
//            expectedDto.setDate(entity.getDate());
//            expectedDto.setClinic(entity.getClinic());
//            expectedDto.setUser(entity.getUser());
//            expectedDto.setResult(entity.getResult());
//            expectedDto.setDoctorFullName(entity.getDoctorFullName());
//            expectedDto.setDoctorSpecialization(entity.getDoctorSpecialization());
//            return expectedDto;
//        }).collect(Collectors.toSet());
//    }
//
//    public static Set<TournamentDto> getExpectedTournamentDto() {
//        Set<Tournament> expectedTournament = getExpectedTournamentsFromDB();
//        return expectedTournament.stream().map(entity -> {
//            var expectedDto = new TournamentDto();
//            expectedDto.setId(entity.getId());
//            expectedDto.setUser(entity.getUser());
//            expectedDto.setResult(entity.getResult());
//            expectedDto.setDate(entity.getDate());
//            expectedDto.setReflection(entity.getReflection());
//            expectedDto.setTitle(entity.getTitle());
//            expectedDto.setOpponent(entity.getOpponent());
//            return expectedDto;
//        }).collect(Collectors.toSet());
//    }

}
