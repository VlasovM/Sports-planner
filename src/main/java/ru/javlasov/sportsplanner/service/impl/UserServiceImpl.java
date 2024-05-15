package ru.javlasov.sportsplanner.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.javlasov.sportsplanner.dto.UserDto;
import ru.javlasov.sportsplanner.expection.NotFoundException;
import ru.javlasov.sportsplanner.mapper.UserCredentialsMapper;
import ru.javlasov.sportsplanner.model.Sport;
import ru.javlasov.sportsplanner.model.User;
import ru.javlasov.sportsplanner.model.UserCredentials;
import ru.javlasov.sportsplanner.repository.UserCredentialsRepository;
import ru.javlasov.sportsplanner.repository.UserRepository;
import ru.javlasov.sportsplanner.service.UserService;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserServiceImpl implements UserService {

    private final UserCredentialsRepository userCredentialsRepository;

    private final UserRepository userRepository;

    private final UserCredentialsMapper userCredentialsMapper;

    @Override
    public UserDto getInfoAuthorizedUser() {
        var emailCurrentUser = SecurityContextHolder.getContext().getAuthentication().getName();
        var currentUser = userCredentialsRepository.findByEmail(emailCurrentUser)
                .orElseThrow(() -> {
                    log.error("Ошибка при попытке найти пользователя по email %s".formatted(emailCurrentUser));
                    throw new NotFoundException("Возникла ошибка с получением данных," +
                            " обратитесь к администратору системы.");
                });

        return userCredentialsMapper.modelToDto(currentUser);
    }

    @Override
    public UserDto editProfile(UserDto userDto) {
        var userCredentials = userCredentialsRepository.findUserByUserId(userDto.getId())
                .orElseThrow(() -> {
                    var errorMessage = "Не найден пользователь с id = %d".formatted(userDto.getId());
                    log.error(errorMessage);
                    return new NotFoundException(errorMessage);
                });
        var user = userCredentials.getUser();
        updateUserInfo(user, userDto);
        user = userRepository.save(user);
        userCredentials.setUser(user);
        return userCredentialsMapper.modelToDto(userCredentials);
    }

    private void updateUserInfo(User user, UserDto userDto) {
        user.setName(userDto.getName());
        user.setMiddleName(userDto.getMiddleName());
        user.setSurname(userDto.getSurname());
        user.setAge(userDto.getAge());
        user.setBirthday(userDto.getBirthday());
        user.setBiography(userDto.getBiography());
    }

    @Override
    public void createProfile(UserDto userDto) {
        var createdUserCredentials = new UserCredentials();
        createdUserCredentials.setEmail(userDto.getEmail());
        createdUserCredentials.setPassword(userDto.getPassword());
        createdUserCredentials.setUser(createNewUser(userDto));
        userCredentialsRepository.save(createdUserCredentials);
    }

    private User createNewUser(UserDto userDto) {
        User createdUser = new User();
        createdUser.setName(userDto.getName());
        createdUser.setMiddleName(userDto.getMiddleName());
        createdUser.setSurname(userDto.getSurname());
        createdUser.setAge(userDto.getAge());
        createdUser.setBirthday(userDto.getBirthday());
        createdUser.setBiography(userDto.getBiography());
        createdUser.setSport(new Sport(userDto.getSport().getId(), userDto.getSport().getTitle()));
        return createdUser;
    }

}
