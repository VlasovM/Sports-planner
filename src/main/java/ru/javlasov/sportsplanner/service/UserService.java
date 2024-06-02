package ru.javlasov.sportsplanner.service;

import ru.javlasov.sportsplanner.dto.UserDto;
import ru.javlasov.sportsplanner.model.User;

public interface UserService {

    UserDto getInfoAuthorizedUser();

    void editProfile(UserDto userDto);

    void createProfile(UserDto userDto);

    User getUserById(Long userId);

}
