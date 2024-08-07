package ru.javlasov.planner.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javlasov.planner.dto.LoggerEvent;
import ru.javlasov.planner.dto.UserDto;
import ru.javlasov.planner.enums.TypeMessage;
import ru.javlasov.planner.expection.NotFoundException;
import ru.javlasov.planner.mapper.UserCredentialsMapper;
import ru.javlasov.planner.mapper.UserMapper;
import ru.javlasov.planner.model.User;
import ru.javlasov.planner.model.UserCredentials;
import ru.javlasov.planner.repository.UserCredentialsRepository;
import ru.javlasov.planner.repository.UserRepository;
import ru.javlasov.planner.service.LoggingService;
import ru.javlasov.planner.service.RoleService;
import ru.javlasov.planner.service.UserCredentialsService;
import ru.javlasov.planner.service.UserService;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserCredentialsRepository userCredentialsRepository;

    private final UserRepository userRepository;

    private final UserCredentialsMapper userCredentialsMapper;

    private final UserCredentialsService userCredentialsService;

    private final LoggingService loggingService;

    private final RoleService roleService;

    private final UserMapper userMapper;

    @Override
    @Transactional(readOnly = true)
    public UserDto getInfoAuthorizedUser() {
        var currentUser = userCredentialsService.getCurrentAuthUser();
        return userCredentialsMapper.modelToDto(currentUser);
    }

    @Override
    @Transactional
    public void editProfile(UserDto userDto) {
        var user = userMapper.dtoToModel(userDto);
        user = userRepository.save(user);
        sendMessage("Пользователь %s изменил информацию о себе"
                .formatted(user.getSurname() + " " + user.getName()), TypeMessage.INFO);
    }

    @Override
    @Transactional
    public void createProfile(UserDto userDto) {
        var createdUserCredentials = createNewUserCredentials(userDto);
        var user = userMapper.dtoToModel(userDto);
        createdUserCredentials.setUser(user);
        createdUserCredentials = userCredentialsRepository.save(createdUserCredentials);
        sendMessage("Создан новый пользователь id = %d, email = %s".formatted(createdUserCredentials.getUser().getId(),
                        createdUserCredentials.getEmail()),
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

    @Override
    public List<User> findUsersForClinicRequest(String name, String surname, String middleName, LocalDate birthday) {
        return userRepository.findUsersForClinicRequest(name, middleName, surname, birthday);
    }

    private void sendMessage(String message, TypeMessage type) {
        var loggingDto = new LoggerEvent(message, type);
        loggingService.sendMessage(loggingDto);
    }

    private UserCredentials createNewUserCredentials(UserDto userDto) {
        var createdUserCredentials = new UserCredentials();
        createdUserCredentials.setEmail(userDto.getEmail());
        setPassword(createdUserCredentials, userDto);
        var role = roleService.getUserRole();
        createdUserCredentials.setRole(role);
        return createdUserCredentials;
    }

    private void setPassword(UserCredentials userCredentials, UserDto userDto) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
        var password = passwordEncoder.encode(userDto.getPassword());
        userCredentials.setPassword(password);
    }

}
