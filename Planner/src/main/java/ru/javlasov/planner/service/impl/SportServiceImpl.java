package ru.javlasov.planner.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.javlasov.planner.dto.SportDto;
import ru.javlasov.planner.mapper.SportMapper;
import ru.javlasov.planner.repository.SportRepository;
import ru.javlasov.planner.service.SportService;

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
