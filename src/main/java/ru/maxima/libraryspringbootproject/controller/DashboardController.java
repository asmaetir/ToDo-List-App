package ru.maxima.libraryspringbootproject.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import ru.maxima.libraryspringbootproject.model.User;
import ru.maxima.libraryspringbootproject.security.UsersDetails;
import ru.maxima.libraryspringbootproject.service.HabitService;
import ru.maxima.libraryspringbootproject.service.TaskService;


@Controller
@RequestMapping("/auth")
public class DashboardController {
    private TaskService taskService;
    private HabitService habitService;


    @Autowired
    public DashboardController(TaskService taskService) {
        this.taskService = taskService;
    }
    public DashboardController(HabitService habitService) {
        this.habitService = habitService;
    }

    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UsersDetails userDetails = (UsersDetails) authentication.getPrincipal();
        User currentUser = userDetails.getUser();

        double dailyProgress = taskService.getDailyProgress(currentUser);
        double weeklyProgress = taskService.getWeeklyProgress(currentUser);
        double monthlyProgress = taskService.getMonthlyProgress(currentUser);

        model.addAttribute("dailyProgress", dailyProgress);
        model.addAttribute("weeklyProgress", weeklyProgress);
        model.addAttribute("monthlyProgress", monthlyProgress);

        return "auth/dashboard";
    }


}
