package ru.javlasov.sportsplanner.mapper;

import org.mapstruct.Mapper;
import ru.javlasov.sportsplanner.dto.UserDto;
import ru.javlasov.sportsplanner.model.User;
import ru.javlasov.sportsplanner.model.UserCredentials;

@Mapper(componentModel = "spring")
public interface UserCredentialsMapper {

    default UserDto modelToDto(UserCredentials model) {
        if (model == null || model.getUser() == null) {
            return null;
        }
        User user = model.getUser();
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setSport(user.getSport());
        userDto.setName(user.getName());
        userDto.setMiddleName(user.getMiddleName());
        userDto.setSurname(user.getSurname());
        userDto.setAge(user.getAge());
        userDto.setBiography(user.getBiography());
        userDto.setEmail(model.getEmail());
        userDto.setBirthday(user.getBirthday());

        return userDto;
    }

}
