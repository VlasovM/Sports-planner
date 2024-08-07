package ru.javlasov.planner.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import ru.javlasov.planner.model.User;

import java.time.LocalDate;
import java.util.List;

public interface UserRepository extends ListCrudRepository<User, Long> {

    @Query("SELECT user FROM User user " +
            "WHERE user.name = :name " +
            "AND user.surname = :surname " +
            "AND user.middleName = :middleName " +
            "AND user.birthday = :birtday")
    List<User> findUsersForClinicRequest(String name, String middleName, String surName, LocalDate birthday);

}
