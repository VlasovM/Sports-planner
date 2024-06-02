package ru.javlasov.sportsplanner.service;

import ru.javlasov.sportsplanner.dto.SportDto;

import java.util.List;

public interface SportService {

    List<SportDto> getAllSports();

}
