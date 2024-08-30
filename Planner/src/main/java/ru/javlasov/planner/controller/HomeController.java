package ru.javlasov.planner.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private static final String ROLE_ADMIN = "[ROLE_ADMIN]";

    @GetMapping("/")
    public String startPage() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            return "homePageNotAuth";
        } else {
            boolean isModerator = checkUser(authentication);
            return isModerator ? "homePageAdmin" : "homePageAuth";
        }
    }

    @GetMapping("/application/login")
    public String login() {
        return "redirect:/";
    }

    @GetMapping("/application/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:/";
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    private boolean checkUser(Authentication authentication) {
        var role = authentication.getAuthorities().toString();
        return role.equals(ROLE_ADMIN);
    }

}
