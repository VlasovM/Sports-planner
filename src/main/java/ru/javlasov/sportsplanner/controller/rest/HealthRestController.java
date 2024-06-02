package ru.javlasov.sportsplanner.controller.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.javlasov.sportsplanner.dto.HealthDto;
import ru.javlasov.sportsplanner.service.HealthService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/healths")
public class HealthRestController {

    private final HealthService healthService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<HealthDto> getAllHealthForCurrentUser () {
        return healthService.getHealthCurrentUser();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteHealthById(@PathVariable("id") Long id) {
        healthService.deleteById(id);
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public void updateHealth(@Valid @RequestBody HealthDto healthDto) {
        healthService.createOrEdit(healthDto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createHealth(@Valid @RequestBody HealthDto healthDto) {
        healthService.createOrEdit(healthDto);
    }

}
