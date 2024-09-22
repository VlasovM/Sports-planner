package ru.javlasov.planner.controller.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.javlasov.planner.dto.UserDto;
import ru.javlasov.planner.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserRestController {

    private final UserService userService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public UserDto getUserInformation() {
        return userService.getInfoAuthorizedUser();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void registration(@Valid @RequestBody UserDto userDto) {
        userService.createProfile(userDto);
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public void editProfile(@Valid @RequestBody UserDto userDto) {
        userService.editProfile(userDto);
    }

}
