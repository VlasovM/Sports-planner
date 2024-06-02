package ru.javlasov.sportsplanner.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.javlasov.sportsplanner.dto.SportDto;
import ru.javlasov.sportsplanner.mapper.SportMapper;
import ru.javlasov.sportsplanner.repository.SportRepository;
import ru.javlasov.sportsplanner.service.SportService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SportServiceImpl implements SportService {

    private final SportRepository sportRepository;

    private final SportMapper sportMapper;

    @Override
    public List<SportDto> getAllSports() {
        return sportMapper.listModelToListDto(sportRepository.findAll());
    }

}
