package ru.maxima.libraryspringbootproject.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import ru.maxima.libraryspringbootproject.model.TaskType;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.maxima.libraryspringbootproject.model.Eisen;
import ru.maxima.libraryspringbootproject.repositories.UserRepository;
import ru.maxima.libraryspringbootproject.service.EisenService;

import java.util.List;

@Controller
@RequestMapping("/auth")

public class EisenController {
    @Autowired
    private EisenService eisenService;
    @Autowired

    private UserRepository userRepository;

    @GetMapping("/eisen")
    public String showParkPage(Model model) {

        List<Eisen> importantUrgentTasks = eisenService.getTasksByType(TaskType.IMPORTANT_URGENT);
        List<Eisen> importantNotUrgentTasks = eisenService.getTasksByType(TaskType.IMPORTANT_NOT_URGENT);
        List<Eisen> notImportantUrgentTasks = eisenService.getTasksByType(TaskType.NOT_IMPORTANT_URGENT);
        List<Eisen> notImportantNotUrgentTasks = eisenService.getTasksByType(TaskType.NOT_IMPORTANT_NOT_URGENT);
        model.addAttribute("importantUrgentTasks", importantUrgentTasks);
        model.addAttribute("importantNotUrgentTasks", importantNotUrgentTasks);
        model.addAttribute("notImportantUrgentTasks", notImportantUrgentTasks);
        model.addAttribute("notImportantNotUrgentTasks", notImportantNotUrgentTasks);
        model.addAttribute("taskIU", new Eisen());
        model.addAttribute("taskINU", new Eisen());
        model.addAttribute("taskNU", new Eisen());
        model.addAttribute("taskNUNU", new Eisen());
        return "auth/eisen";
    }


       @PostMapping("/todoIU")
    public String addTaskToImportantUrgentList(@ModelAttribute("taskIU") Eisen taskIU, @RequestParam(value = "checkboxIsChecked", required = false) boolean checkboxIsChecked, Authentication authentication, Model model) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        taskIU.setType(TaskType.IMPORTANT_URGENT);
        taskIU.setIsComplete(checkboxIsChecked);
        eisenService.savetaskIU(taskIU, username);
        List<Eisen> importantUrgentTasks = eisenService.getTasksByType(TaskType.IMPORTANT_URGENT);
        model.addAttribute("importantUrgentTasks", importantUrgentTasks);
        model.addAttribute("taskIU", new Eisen());
        return "redirect:/auth/eisen";
    }

    @PostMapping("/todoINU")
    public String addTaskToImportantNotUrgentList(@ModelAttribute("taskINU")Eisen taskINU, Authentication authentication, Model model) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        taskINU.setType(TaskType.IMPORTANT_NOT_URGENT);
        eisenService.save(taskINU, username);
        List<Eisen> importantNotUrgentTasks = eisenService.getTasksByType(TaskType.IMPORTANT_NOT_URGENT);
        model.addAttribute("importantNotUrgentTasks", importantNotUrgentTasks);
        model.addAttribute("taskINU", new Eisen());
        return "redirect:/auth/eisen";
    }

    @PostMapping("/todoNU")
    public String addTaskToNotImportantUrgentList(@ModelAttribute("taskNU")Eisen taskNU, Authentication authentication, Model model) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        taskNU.setType(TaskType.NOT_IMPORTANT_URGENT);
        eisenService.save(taskNU, username);
        List<Eisen> notImportantUrgentTasks = eisenService.getTasksByType(TaskType.NOT_IMPORTANT_URGENT);
        model.addAttribute("notImportantUrgentTasks", notImportantUrgentTasks);
        model.addAttribute("taskNU", new Eisen());
        return "redirect:/auth/eisen";
    }

    @PostMapping("/todoNUNU")
    public String addTaskToNotImportantNotUrgentList(@ModelAttribute("taskNUNU")Eisen taskNUNU, Authentication authentication, Model model) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        taskNUNU.setType(TaskType.NOT_IMPORTANT_NOT_URGENT);
        eisenService.save(taskNUNU, username);
        List<Eisen> notImportantNotUrgentTasks = eisenService.getTasksByType(TaskType.NOT_IMPORTANT_NOT_URGENT);
        model.addAttribute("notImportantNotUrgentTasks", notImportantNotUrgentTasks);
        model.addAttribute("taskNUNU", new Eisen());
        return "redirect:/auth/eisen";
    }
    @GetMapping("/deleteE/{id}")
    public String deleteTaskEisen(@PathVariable("id") Long TaskId, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        Eisen eisen = eisenService.findById(TaskId);

        if (eisen!= null && eisen.getUser().getUsername().equals(username)) {
            eisenService.delete(eisen);
        }

        return "redirect:/auth/eisen";
    }

}