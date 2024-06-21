package ru.javlasov.planner.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.javlasov.planner.dto.SportDto;
import ru.javlasov.planner.service.SportService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/sports")
public class SportRestController {

    private final SportService sportService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<SportDto> getAllSports() {
        return sportService.getAllSports();
    }

}
