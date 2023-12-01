package ru.maxima.libraryspringbootproject.controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import ru.maxima.libraryspringbootproject.model.TodoItem;
import ru.maxima.libraryspringbootproject.repositories.UserRepository;
import ru.maxima.libraryspringbootproject.service.ParkService;

import java.util.List;


@Controller
@RequestMapping("/auth")

public class ParkController {
    @Autowired
    private ParkService parkService;
    private UserRepository userRepository;


    @GetMapping("/park")
    public String getUserTasks(Model model) {
        List<TodoItem> todoItems = parkService.getTasksByCurrentUser();
        model.addAttribute("todoItems", todoItems);
        model.addAttribute("todoItem", new TodoItem());
        return "auth/park";
    }

    @PostMapping("/todo")
    public String addTask(@ModelAttribute("todoItem") TodoItem todoItem, Authentication authentication, Model model) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        parkService.save(todoItem, username);

        // Refresh the task list after adding the new task
        List<TodoItem> todoItems = parkService.getTasksByCurrentUser();
        model.addAttribute("todoItems", todoItems);
        model.addAttribute("todoItem", new TodoItem());

        return "auth/park";
    }

    @PostMapping("/searchpark")
    public String searchTasks(@RequestParam("searchpark") String searchpark, Authentication authentication, Model model) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        List<TodoItem> filteredTasks = parkService.searchTasksByUsername(username, searchpark);
        model.addAttribute("todoItems", filteredTasks);
        model.addAttribute("username", username);

        return "auth/park";
    }
    @GetMapping("/edit/{id}")
    public String showUpdateTaskForm(@PathVariable("id") Long parkId, Model model) {
        TodoItem todoItem = parkService.getById(parkId)
                .orElseThrow(() -> new IllegalArgumentException("Task id: " + parkId + " not found"));

        model.addAttribute("todoItem", todoItem);
        return "auth/edit-park";
    }
    @PostMapping("/todo/{id}")
    public String updateTask(@PathVariable("id") Long parkId, @ModelAttribute("todoItem") TodoItem updatedTask, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        TodoItem todoItem = parkService.getById(parkId)
                .orElseThrow(() -> new IllegalArgumentException("Task id: " + parkId + " not found"));

        todoItem.setDescription(updatedTask.getDescription());
        todoItem.setIsComplete(updatedTask.getIsComplete());

       parkService.save(todoItem, username);

        return "redirect:/auth/park";
    }



    @GetMapping("/delete/{id}")
    public String deleteTask(@PathVariable("id") Long parkId, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        TodoItem todoItem = parkService.findById(parkId);

        if (todoItem != null && todoItem.getUser().getUsername().equals(username)) {
            parkService.delete(todoItem);
        }

        return "redirect:/auth/park";
    }

}



