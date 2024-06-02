package ru.javlasov.sportsplanner.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.javlasov.sportsplanner.dto.SportDto;
import ru.javlasov.sportsplanner.service.SportService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/sports")
public class SportController {

    private final SportService sportService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<SportDto> getAllSports() {
        return sportService.getAllSports();
    }

}
