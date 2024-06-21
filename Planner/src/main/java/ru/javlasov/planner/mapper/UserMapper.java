package ru.javlasov.planner.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.javlasov.planner.dto.UserDto;
import ru.javlasov.planner.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "email", ignore = true)
    @Mapping(target = "password", ignore = true)
    UserDto modelToDto(User model);

    User dtoToModel(UserDto dto);

}
