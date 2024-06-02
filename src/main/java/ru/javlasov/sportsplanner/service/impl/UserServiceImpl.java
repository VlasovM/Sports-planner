package ru.javlasov.sportsplanner.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javlasov.sportsplanner.dto.LoggerEvent;
import ru.javlasov.sportsplanner.dto.UserDto;
import ru.javlasov.sportsplanner.enums.TypeMessage;
import ru.javlasov.sportsplanner.expection.NotFoundException;
import ru.javlasov.sportsplanner.mapper.UserCredentialsMapper;
import ru.javlasov.sportsplanner.model.User;
import ru.javlasov.sportsplanner.model.UserCredentials;
import ru.javlasov.sportsplanner.repository.UserCredentialsRepository;
import ru.javlasov.sportsplanner.repository.UserRepository;
import ru.javlasov.sportsplanner.service.LoggingService;
import ru.javlasov.sportsplanner.service.RoleService;
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

    private final RoleService roleService;

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
        setPassword(createdUserCredentials, userDto);
        var role = roleService.getUserRole();
        createdUserCredentials.setRole(role);
        createdUserCredentials.setUser(createNewUser(userDto));
        var newUser = userCredentialsRepository.save(createdUserCredentials);
        sendMessage("Создан новый пользователь id = %d, email = %s".formatted(newUser.getId(), newUser.getEmail()),
                TypeMessage.INFO);
    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> {
                    var errorMessage = "Не найден пользователь с id = %d".formatted(userId);
                    sendMessage(errorMessage, TypeMessage.ERROR);
                    return new NotFoundException(errorMessage);
                });
    }

    private User createNewUser(UserDto userDto) {
        User createdUser = new User();
        createdUser.setName(userDto.getName());
        createdUser.setMiddleName(userDto.getMiddleName());
        createdUser.setSurname(userDto.getSurname());
        createdUser.setAge(userDto.getAge());
        createdUser.setBirthday(userDto.getBirthday());
        createdUser.setBiography(userDto.getBiography());
        createdUser.setSport(userDto.getSport());
        return createdUser;
    }

    private void sendMessage(String message, TypeMessage type) {
        var loggingDto = new LoggerEvent(message, type);
        loggingService.sendMessage(loggingDto);
    }

    private void setPassword(UserCredentials userCredentials, UserDto userDto) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
        var password = passwordEncoder.encode(userDto.getPassword());
        userCredentials.setPassword(password);
    }

}
