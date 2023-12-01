package ru.maxima.libraryspringbootproject.controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import ru.maxima.libraryspringbootproject.model.Habit;
import ru.maxima.libraryspringbootproject.repositories.UserRepository;
import ru.maxima.libraryspringbootproject.service.HabitService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.time.LocalDate;
import java.util.List;


@Controller
@RequestMapping("/auth")

public class HabitController {
    @Autowired
    private HabitService habitService;
    private UserRepository userRepository;


    @GetMapping("/habits")
    public String getUserHabits(Model model) {
        List<Habit> habits = habitService.getTasksByCurrentUser();
        model.addAttribute("habits", habits);
        model.addAttribute("habit", new Habit());
        return "auth/habits";
    }

    @PostMapping("/listhabit")
    public String addTask(@ModelAttribute("habit") Habit habit, Authentication authentication, Model model) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        habitService.save(habit, username);

        List<Habit> habits = habitService.getTasksByCurrentUser();
        model.addAttribute("habits", habits);
        model.addAttribute("habit", new Habit());
        return "auth/habits";

    }

    @PostMapping("/store-habit")
    public String storeHabit(@RequestParam("habit") String habitDescription,
                             @RequestParam(value = "addHabit", required = false) String addHabit, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        if (addHabit != null) {
            Habit habit = new Habit();
            habit.setName(habitDescription);
            habit.setDate(LocalDate.now());
            habitService.save(habit,username);
            //lastHabit = habitDescription;
        }

        return "redirect:/auth/habits";
    }


    @PostMapping("/update-progress/{id}")
    public String updateProgress(@PathVariable("id") Long id,
                                 @RequestParam("frequency") int frequency,
                                 Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        Habit habit = habitService.getHabitByIdAndUsername(id, username);
        if (habit != null) {
            int currentProgress = habit.getProgress();
            int updatedProgress = currentProgress;

            switch (frequency) {
                case 1: // Daily
                    updatedProgress += 3;
                    break;
                case 2: // Every Other Day
                    updatedProgress += 6;
                    break;
                case 7: // Weekly
                    updatedProgress += 25;
                    break;
            }

            updatedProgress = Math.min(updatedProgress, 100);
            habit.setProgress(updatedProgress);
            habitService.save(habit,username);
        }

        return "redirect:/auth/habits";
    }

    @GetMapping("/deletehabit/{id}")
    public String deleteHabit(@PathVariable("id") Long id, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

       Habit todoItem = habitService.findById(id);

        if (todoItem != null && todoItem.getUser().getUsername().equals(username)) {
            habitService.delete(todoItem);
        }

        return "redirect:/auth/habits";
    }

}


