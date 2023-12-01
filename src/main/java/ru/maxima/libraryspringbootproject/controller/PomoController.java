package ru.maxima.libraryspringbootproject.controller;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;



@Controller
@RequestMapping("/auth")

public class PomoController {

    @GetMapping("/pomodoro")
    public String pomodoroPage(Model model, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        return "auth/pomodoro";
    }
    @GetMapping("/shortbreak")
    public String shortbreak(Model model, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        return "auth/shortbreak";
    }
    @GetMapping("/Long")
    public String Long(Model model, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        return "auth/Long";
    }
    @GetMapping("/time")
    public String timem(Model model, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        return "auth/time";
    }
}
