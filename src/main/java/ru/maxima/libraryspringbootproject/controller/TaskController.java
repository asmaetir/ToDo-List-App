package ru.maxima.libraryspringbootproject.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import ru.maxima.libraryspringbootproject.model.Task;
import ru.maxima.libraryspringbootproject.repositories.UserRepository;
import ru.maxima.libraryspringbootproject.security.UsersDetails;
import ru.maxima.libraryspringbootproject.service.TaskService;

import java.util.List;


@Controller
@RequestMapping("/auth")

public class TaskController {
   private TaskService taskService;
    private UserRepository userRepository;

    public TaskController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }
    @GetMapping("/profil")
    public String showUserProfile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        model.addAttribute("username", username);
        return "/auth/list";
    }

    @GetMapping("/list")

    public String getUserTasks(@AuthenticationPrincipal UsersDetails userDetails, Model model) {
    List<Task> tasks = taskService.getTasksByCurrentUser();
    model.addAttribute("tasks", tasks);
    return "auth/list";
}

    @PostMapping("/search")
    public String searchTasks(@RequestParam("searchText") String searchText, Authentication authentication, Model model) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        List<Task> filteredTasks = taskService.searchTasksByUsername(username, searchText);
        model.addAttribute("tasks", filteredTasks);
        model.addAttribute("username", username);
        return "auth/list";
    }

    @GetMapping("/create")
    public String showCreateTaskForm(Model model) {
      model.addAttribute("task", new Task());
        return "auth/new-task";
    }
    @PostMapping("/save")
    public String addTask(@ModelAttribute("task") Task task, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        Task item = new Task();
        item.setDescription(task.getDescription());
        item.setIsComplete(task.getIsComplete());
        taskService.save(task, username);

        return "redirect:/auth/list";
    }
    @GetMapping("/editask/{id}")
    public String showUpdateTaskForm(@PathVariable("id") Long id, Model model) {
        Task task = taskService.getById(id)
                .orElseThrow(() -> new IllegalArgumentException("Task id: " + id + " not found"));

        model.addAttribute("task", task);
        return "auth/edit-task";
    }
    @PostMapping("/doto/{id}")
    public String updateTask(@PathVariable("id") Long id, @ModelAttribute("task") Task updatedTask, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        Task task = taskService.getById(id)
                .orElseThrow(() -> new IllegalArgumentException("Task id: " + id + " not found"));

        task.setDescription(updatedTask.getDescription());
        task.setIsComplete(updatedTask.getIsComplete());

        taskService.save(task, username);

        return "redirect:/auth/list";
    }



    @GetMapping("/deleteTask/{id}")
    public String deleteTask(@PathVariable("id") Long id, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        Task task = taskService.findById(id);

        if (task != null && task.getUser().getUsername().equals(username)) {
            taskService.delete(task);
        }

        return "redirect:/auth/list";
    }



}



