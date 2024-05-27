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
import ru.javlasov.sportsplanner.dto.TournamentDto;
import ru.javlasov.sportsplanner.service.TournamentService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tournaments")
public class TournamentController {

    private final TournamentService tournamentService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TournamentDto> getAllTournamentForCurrentUser() {
        return tournamentService.getTournamentCurrentUser();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTournamentById(@PathVariable("id") Long id) {
        tournamentService.deleteById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createTournament(@Valid @RequestBody TournamentDto tournamentDto) {
        tournamentService.createOrEdit(tournamentDto);
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public void updateTournament(@Valid @RequestBody TournamentDto tournamentDto) {
        tournamentService.createOrEdit(tournamentDto);
    }

}
