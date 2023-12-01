package ru.maxima.libraryspringbootproject.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Service;
import ru.maxima.libraryspringbootproject.model.TodoItem;
import ru.maxima.libraryspringbootproject.model.User;
import ru.maxima.libraryspringbootproject.repositories.ParkRepository;
import ru.maxima.libraryspringbootproject.repositories.TaskRepository;
import ru.maxima.libraryspringbootproject.model.Task;
import ru.maxima.libraryspringbootproject.repositories.UserRepository;
import ru.maxima.libraryspringbootproject.security.UsersDetails;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ParkService {
    @Autowired
    private ParkRepository parkRepository;
    private UserRepository userRepository;

    @Autowired
    public  ParkService(@Lazy ParkRepository parkRepository, @Lazy UserRepository userRepository) {
        this.parkRepository = parkRepository;
        this.userRepository = userRepository;
    }



    public TodoItem save(@Valid TodoItem todoItem, String username) {
        User user = userRepository.findByUsername(username);
        todoItem.setUser(user);
        if (todoItem.getParkId() == null) {
            todoItem.setDate(Instant.now());
        }
        todoItem.setUpdatedAt(Instant.now());
        return parkRepository.save(todoItem);
    }

    public List<TodoItem> searchTasksByUsername(String username,String searchpark) {
        List<TodoItem> todoItems = parkRepository.findByUserUsername(username);
        return todoItems;
    }
    public List<TodoItem> getTasksByUserAndDate(User user, Instant date) {
        return parkRepository.findByUserAndDate(user, date);
    }

    /*
    public List<TodoItem> getTasksByUserAndDate(User user, Instant date) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UsersDetails userDetails = (UsersDetails) authentication.getPrincipal();
        User currentUser = userDetails.getUser();

        if (user.getId()==(currentUser.getId())) {
            return parkRepository.findByUserAndDate(currentUser, date);
        } else {
            return null;
        }
    }*/

  public List<TodoItem> getTasksByCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UsersDetails userDetails = (UsersDetails) authentication.getPrincipal();
        User currentUser = userDetails.getUser();
        return parkRepository.findByUser(currentUser);
    }

    public void delete(TodoItem todoItem) {
        parkRepository.delete(todoItem);}

    public Optional<TodoItem> getById(Long id) {
        return parkRepository.findById(id);
    }
    public TodoItem findById(Long parkId) {
        return parkRepository.findById(parkId).orElse(null);
    }

/*
    public double getCompletionProgress() {
         double totalItems =parkRepository.count();
         double completedItems = parkRepository.countAllByIsCompleteIsTrue();
         if (totalItems == 0) {
             return 0;
         }
         return  (completedItems / totalItems)*100.0;
     }
*/

}



