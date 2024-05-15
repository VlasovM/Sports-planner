package ru.javlasov.sportsplanner.service;

import ru.javlasov.sportsplanner.dto.UserDto;

public interface UserService {

    UserDto getInfoAuthorizedUser();

    UserDto editProfile(UserDto userDto);

    void createProfile(UserDto userDto);

}
