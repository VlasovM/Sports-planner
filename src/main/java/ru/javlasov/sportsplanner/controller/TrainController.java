package ru.javlasov.sportsplanner.controller;

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
import ru.javlasov.sportsplanner.dto.TrainDto;
import ru.javlasov.sportsplanner.service.TrainService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/trains")
public class TrainController {

    private final TrainService trainService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TrainDto> getAllTrains() {
        return trainService.getAllTrainsCurrentUser();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTrainById(@PathVariable("id") Long id) {
        trainService.deleteById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createTrain(@Valid @RequestBody TrainDto trainDto) {
        trainService.createOrEdit(trainDto);
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public void editTrain(@Valid @RequestBody TrainDto trainDto) {
        trainService.createOrEdit(trainDto);
    }

}
