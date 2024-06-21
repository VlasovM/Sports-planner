package ru.javlasov.planner.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.javlasov.planner.dto.TournamentDto;
import ru.javlasov.planner.service.TournamentService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/tournaments")
public class TournamentController {

    private final TournamentService tournamentService;

    @GetMapping
    public String tournaments() {
        return "tournaments";
    }

    @GetMapping("/create")
    public String createTournament() {
        return "createTournament";
    }

    @GetMapping("/edit/{id}")
    public String editTournament(@PathVariable(name = "id") Long id, Model model) {
        TournamentDto tournamentDto = tournamentService.getById(id);
        model.addAttribute("id", id);
        model.addAttribute("title", tournamentDto.getTitle());
        model.addAttribute("date", tournamentDto.getDate());
        model.addAttribute("opponent", tournamentDto.getOpponent());
        model.addAttribute("result", tournamentDto.getResult());
        model.addAttribute("reflection", tournamentDto.getReflection());
        return "editTournament";
    }

}
