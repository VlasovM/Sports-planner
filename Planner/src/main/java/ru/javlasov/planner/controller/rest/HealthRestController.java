package ru.javlasov.planner.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.javlasov.planner.dto.HealthDto;
import ru.javlasov.planner.service.HealthService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/healths")
public class HealthRestController {

    private final HealthService healthService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<HealthDto> getAllHealthForCurrentUser() {
        return healthService.getHealthCurrentUser();
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public HealthDto getHealthById(@PathVariable("id") Long id) {
        return healthService.getById(id);
    }

}
