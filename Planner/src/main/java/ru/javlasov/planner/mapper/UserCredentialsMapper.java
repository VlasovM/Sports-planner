package ru.javlasov.planner.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.javlasov.planner.dto.UserDto;
import ru.javlasov.planner.model.UserCredentials;

@Mapper(componentModel = "spring")
public interface UserCredentialsMapper {

    default UserDto modelToDto(UserCredentials model) {
        if (model == null) {
            return null;
        }

        UserMapper userMapper = Mappers.getMapper(UserMapper.class);
        UserDto userDto = userMapper.modelToDto(model.getUser());
        userDto.setEmail(model.getEmail());
        userDto.setPassword(model.getPassword());

        return userDto;
    }

}
