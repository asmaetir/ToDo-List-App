package ru.maxima.libraryspringbootproject.service;

import java.time.*;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Service;
import ru.maxima.libraryspringbootproject.model.User;
import ru.maxima.libraryspringbootproject.repositories.TaskRepository;
import ru.maxima.libraryspringbootproject.model.Task;
import ru.maxima.libraryspringbootproject.repositories.UserRepository;
import ru.maxima.libraryspringbootproject.security.UsersDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;
    private UserRepository userRepository;

    @Autowired
    public TaskService(@Lazy TaskRepository taskRepository, @Lazy UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }


    public Optional<Task> getById(Long id) {
        return taskRepository.findById(id);
    }

    public List<Task> getAll() {
        return taskRepository.findAll();
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }
    public Task save(Task task, String username) {
        User user = userRepository.findByUsername(username);
        task.setUser(user);
        if (task.getId() == null) {
            LocalDateTime now = LocalDateTime.now();
            task.setCreatedAt(now);
        }
        task.setUpdatedAt(Instant.now());
        return taskRepository.save(task);
    }
 /* public Task save(Task task, String username) {
        User user = userRepository.findByUsername(username);
        task.setUser(user);
        if (task.getId() == null) {
            task.setCreatedAt(Instant.now());
        }
        task.setUpdatedAt(Instant.now());
        return taskRepository.save(task);
    }
*/
    public List<Task> searchTasksByUsername(String username,String searchText) {
        List<Task> tasks = taskRepository.findByUserUsername(username, searchText);
        return tasks;
    }

    public List<Task> getTasksByCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UsersDetails userDetails = (UsersDetails) authentication.getPrincipal();
        User currentUser = userDetails.getUser();
        return taskRepository.findByUser(currentUser);
    }

    public void delete(Task task) {
        taskRepository.delete(task);}

    public double getCompletionProgress() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UsersDetails userDetails = (UsersDetails) authentication.getPrincipal();
        User currentUser = userDetails.getUser();
        long totalTasks = taskRepository.countByUser(currentUser);
        long completedTasks = taskRepository.countByUserAndIsComplete(currentUser, true);

        if (totalTasks == 0) {
            return 0.0;
        }

        return (double) completedTasks / totalTasks * 100.0;
    }

/*
   public double getCompletionProgress(String username, String period) {
       Instant startDate;
       Instant endDate = Instant.now();

       switch (period) {
           case "daily":
               startDate = endDate.truncatedTo(ChronoUnit.DAYS);
               break;
           case "weekly":
               startDate = endDate.minus(7, ChronoUnit.DAYS).truncatedTo(ChronoUnit.DAYS);
               break;
           case "monthly":
               startDate = endDate.minus(1, ChronoUnit.MONTHS).truncatedTo(ChronoUnit.DAYS);
               break;
           default:
               throw new IllegalArgumentException("Invalid period: " + period);
       }

       long totalTasks = taskRepository.countByUserUsernameAndCreatedAtBetween(username, startDate, endDate);
       long completedTasks = taskRepository.countByUserUsernameAndIsCompleteAndCreatedAtBetween(username, true, startDate, endDate);

       if (totalTasks > 0) {
           return (double) completedTasks / totalTasks * 100;
       } else {
           return 0.0;
       }
   }*/


    public Task findById(Long taskId) {
        return taskRepository.findById(taskId).orElse(null);
    }

/*
    public double calculateDailyProgress(String username) {
        LocalDate today = LocalDate.now();
        LocalDate startDate = today.atStartOfDay().toLocalDate();
        LocalDate endDate = today.atTime(23, 59, 59).toLocalDate();

        long totalTasks = taskRepository.countCreated(username, true, startDate, endDate);
        long completedTasks = taskRepository.countByUserUsernameAndIsCompleteAndCreatedAtBetween(username, true, startDate, endDate);

        if (totalTasks == 0) {
            return 0.0;
        }

        return (double) completedTasks / totalTasks * 100;
    }

    public double calculateWeeklyProgress(String username) {
        LocalDate today = LocalDate.now();
        LocalDate startDate = today.minusDays(6).atStartOfDay().toLocalDate();
        LocalDate endDate = today.atTime(23, 59, 59).toLocalDate();

        long totalTasks = taskRepository.countByUserUsernameAndIsCompleteAndCreatedAtBetween(username, true, startDate, endDate);
        long completedTasks = taskRepository.countByUserUsernameAndIsCompleteAndCreatedAtBetween(username, true, startDate, endDate);

        if (totalTasks == 0) {
            return 0.0;
        }

        return (double) completedTasks / totalTasks * 100;
    }

    public double calculateMonthlyProgress(String username) {
        LocalDate today = LocalDate.now();
        LocalDate startDate = today.withDayOfMonth(1).atStartOfDay().toLocalDate();
        LocalDate endDate = today.atTime(23, 59, 59).toLocalDate();

        long totalTasks = taskRepository.countByUserUsernameAndIsCompleteAndCreatedAtBetween(username, true, startDate, endDate);
        long completedTasks = taskRepository.countByUserUsernameAndIsCompleteAndCreatedAtBetween(username, true, startDate, endDate);

        if (totalTasks == 0) {
            return 0.0;
        }

        return (double) completedTasks / totalTasks * 100;
    }
*/



    public List<Task> getTasksByDateRange(LocalDate startDate, LocalDate endDate) {
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX);
        Instant startInstant = startDateTime.atZone(ZoneOffset.UTC).toInstant();
        Instant endInstant = endDateTime.atZone(ZoneOffset.UTC).toInstant();
        return taskRepository.findByCreatedAtBetween(startInstant, endInstant);
    }



    public double getDailyProgress(User user) {
        LocalDate today = LocalDate.now();
        long totalTasks = taskRepository.countByUserAndCreatedAtBetween(user, today.atStartOfDay(), today.plusDays(1).atStartOfDay());
        long completedTasks = taskRepository.countByUserAndIsCompleteAndCreatedAtBetween(user, true, today.atStartOfDay(), today.plusDays(1).atStartOfDay());

        return calculateProgress(totalTasks, completedTasks);
    }

    public double getWeeklyProgress(User user) {
        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.minusDays(today.getDayOfWeek().getValue() - 1);
        LocalDate endOfWeek = today.plusDays(7 - today.getDayOfWeek().getValue());
        long totalTasks = taskRepository.countByUserAndCreatedAtBetween(user, startOfWeek.atStartOfDay(), endOfWeek.plusDays(1).atStartOfDay());
        long completedTasks = taskRepository.countByUserAndIsCompleteAndCreatedAtBetween(user, true, startOfWeek.atStartOfDay(), endOfWeek.plusDays(1).atStartOfDay());

        return calculateProgress(totalTasks, completedTasks);
    }

    public double getMonthlyProgress(User user) {
        LocalDate today = LocalDate.now();
        LocalDate startOfMonth = today.withDayOfMonth(1);
        LocalDate endOfMonth = today.withDayOfMonth(today.lengthOfMonth());
        long totalTasks = taskRepository.countByUserAndCreatedAtBetween(user, startOfMonth.atStartOfDay(), endOfMonth.plusDays(1).atStartOfDay());
        long completedTasks = taskRepository.countByUserAndIsCompleteAndCreatedAtBetween(user, true, startOfMonth.atStartOfDay(), endOfMonth.plusDays(1).atStartOfDay());

        return calculateProgress(totalTasks, completedTasks);
    }

    private double calculateProgress(long totalTasks, long completedTasks) {
        if (totalTasks == 0) {
            return 0.0;
        }

        return (double) completedTasks / totalTasks * 100.0;
    }



}



