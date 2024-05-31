package ru.javlasov.sportsplanner.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javlasov.sportsplanner.dto.LoggerEvent;
import ru.javlasov.sportsplanner.dto.UserDto;
import ru.javlasov.sportsplanner.enums.TypeMessage;
import ru.javlasov.sportsplanner.expection.NotFoundException;
import ru.javlasov.sportsplanner.mapper.UserCredentialsMapper;
import ru.javlasov.sportsplanner.model.Sport;
import ru.javlasov.sportsplanner.model.User;
import ru.javlasov.sportsplanner.model.UserCredentials;
import ru.javlasov.sportsplanner.repository.UserCredentialsRepository;
import ru.javlasov.sportsplanner.repository.UserRepository;
import ru.javlasov.sportsplanner.service.LoggingService;
import ru.javlasov.sportsplanner.service.UserCredentialsService;
import ru.javlasov.sportsplanner.service.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserCredentialsRepository userCredentialsRepository;

    private final UserRepository userRepository;

    private final UserCredentialsMapper userCredentialsMapper;

    private final UserCredentialsService userCredentialsService;

    private final LoggingService loggingService;

    @Override
    @Transactional(readOnly = true)
    public UserDto getInfoAuthorizedUser() {
        var currentUser = userCredentialsService.getCurrentAuthUser();
        return userCredentialsMapper.modelToDto(currentUser);
    }

    @Override
    @Transactional
    public void editProfile(UserDto userDto) {
        var userCredentials = userCredentialsRepository.findUserByUserId(userDto.getId())
                .orElseThrow(() -> {
                    var errorMessage = "Не найден пользователь с id = %d".formatted(userDto.getId());
                    sendMessage(errorMessage, TypeMessage.ERROR);
                    return new NotFoundException(errorMessage);
                });
        var user = userCredentials.getUser();
        updateUserInfo(user, userDto);
        var editedUser = userRepository.save(user);
        sendMessage("Пользователь %s изменил информацию о себе"
                .formatted(editedUser.getSurname() + " " + editedUser.getName()), TypeMessage.INFO);
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
    @Transactional
    public void createProfile(UserDto userDto) {
        var createdUserCredentials = new UserCredentials();
        createdUserCredentials.setEmail(userDto.getEmail());
        createdUserCredentials.setPassword(userDto.getPassword());
        createdUserCredentials.setUser(createNewUser(userDto));
        var newUser = userCredentialsRepository.save(createdUserCredentials);
        sendMessage("Создан новый пользователь id = %d, email = %s".formatted(newUser.getId(), newUser.getEmail()),
                TypeMessage.INFO);
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

    private void sendMessage(String message, TypeMessage type) {
        var loggingDto = new LoggerEvent(message, type);
        loggingService.sendMessage(loggingDto);
    }

}
