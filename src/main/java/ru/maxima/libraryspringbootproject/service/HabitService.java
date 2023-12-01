package ru.maxima.libraryspringbootproject.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Service;
import ru.maxima.libraryspringbootproject.model.Habit;
import ru.maxima.libraryspringbootproject.model.TodoItem;
import ru.maxima.libraryspringbootproject.model.User;
import ru.maxima.libraryspringbootproject.repositories.HabitRepository;
import ru.maxima.libraryspringbootproject.repositories.ParkRepository;
import ru.maxima.libraryspringbootproject.repositories.TaskRepository;
import ru.maxima.libraryspringbootproject.model.Task;
import ru.maxima.libraryspringbootproject.repositories.UserRepository;
import ru.maxima.libraryspringbootproject.security.UsersDetails;

import java.time.Instant;
import java.util.*;

@Service
public class HabitService {
    @Autowired
    private HabitRepository habitRepository;
    private UserRepository userRepository;

    @Autowired
    public  HabitService(@Lazy HabitRepository habitRepository, @Lazy UserRepository userRepository) {
        this. habitRepository =  habitRepository;
        this.userRepository = userRepository;
    }



    public Habit save(@Valid Habit habit, String username) {
        User user = userRepository.findByUsername(username);
        habit.setUser(user);
        if (habit.getId() == null) {
            habit.setDate(LocalDate.now());
        }
        return habitRepository.save(habit);
    }


    public List<Habit> getTasksByUserAndDate(String username, LocalDate date) {
        User user = userRepository.findByUsername(username);

        return habitRepository.findByUserAndDate(user,date);
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

    public List<Habit> getTasksByCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UsersDetails userDetails = (UsersDetails) authentication.getPrincipal();
        User currentUser = userDetails.getUser();
        return habitRepository.findByUser(currentUser);
    }

    public void delete(Habit habit) {
        habitRepository.delete(habit);}

    public Habit getHabitByIdAndUsername(Long id, String username) {
        return habitRepository.findByIdAndUser_Username(id, username);
    }

    public Habit findById(Long id) {return habitRepository.findById(id).orElse(null);
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



