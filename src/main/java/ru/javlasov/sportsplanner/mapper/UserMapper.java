package ru.javlasov.sportsplanner.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.javlasov.sportsplanner.dto.UserDto;
import ru.javlasov.sportsplanner.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "email", ignore = true)
    @Mapping(target = "password", ignore = true)
    UserDto modelToDto(User model);

    User dtoToModel(UserDto dto);

}
