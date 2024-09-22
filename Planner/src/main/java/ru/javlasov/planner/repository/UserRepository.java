package ru.javlasov.planner.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import ru.javlasov.planner.model.User;

import java.time.LocalDate;
import java.util.List;

public interface UserRepository extends ListCrudRepository<User, Long> {

    @Query("SELECT user FROM User user " +
            "WHERE user.name = :name " +
            "AND user.surname = :surname " +
            "AND user.middleName = :middleName " +
            "AND user.birthday = :birthday")
    List<User> findUsersForClinicRequest(@Param(value = "name") String name,
                                         @Param(value = "middleName") String middleName,
                                         @Param(value = "surname") String surname,
                                         @Param(value = "birthday") LocalDate birthday);

}
