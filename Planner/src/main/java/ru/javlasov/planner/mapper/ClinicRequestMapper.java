package ru.javlasov.planner.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.javlasov.planner.dto.ClinicRequestDto;
import ru.javlasov.planner.model.ClinicRequest;

@Mapper(componentModel = "spring")
public interface ClinicRequestMapper {

    @Mapping(source = "patient.name", target = "patientName")
    @Mapping(source = "patient.surname", target = "patientSurname")
    @Mapping(source = "patient.middleName", target = "patientMiddleName")
    @Mapping(source = "patient.birthday", target = "patientBirthday")
    ClinicRequest dtoToEntity(ClinicRequestDto dto);

}
