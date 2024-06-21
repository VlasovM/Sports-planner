package ru.javlasov.planner.service;

import ru.javlasov.planner.dto.UserDto;
import ru.javlasov.planner.model.User;

public interface UserService {

    UserDto getInfoAuthorizedUser();

    void editProfile(UserDto userDto);

    void createProfile(UserDto userDto);

    User getUserById(Long userId);

}
