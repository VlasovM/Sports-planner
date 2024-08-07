package ru.javlasov.planner.service;

import ru.javlasov.planner.dto.UserDto;
import ru.javlasov.planner.model.User;

import java.time.LocalDate;
import java.util.List;

public interface UserService {

    UserDto getInfoAuthorizedUser();

    void editProfile(UserDto userDto);

    void createProfile(UserDto userDto);

    User getUserById(Long userId);

    List<User> findUsersForClinicRequest(String name, String surname, String middleName, LocalDate birthday);

}
