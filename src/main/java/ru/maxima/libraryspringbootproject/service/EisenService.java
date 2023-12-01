package ru.maxima.libraryspringbootproject.service;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.maxima.libraryspringbootproject.model.Eisen;
import ru.maxima.libraryspringbootproject.model.TaskType;
import ru.maxima.libraryspringbootproject.model.TodoItem;
import ru.maxima.libraryspringbootproject.model.User;
import ru.maxima.libraryspringbootproject.repositories.EisenRepository;
import ru.maxima.libraryspringbootproject.repositories.ParkRepository;
import ru.maxima.libraryspringbootproject.repositories.UserRepository;
import ru.maxima.libraryspringbootproject.security.UsersDetails;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
@Service
public class EisenService {
    @Autowired
    private EisenRepository eisenRepository;
    private UserRepository userRepository;

    @Autowired
    public  EisenService(@Lazy EisenRepository eisenRepository, @Lazy UserRepository userRepository) {
        this.eisenRepository = eisenRepository;
        this.userRepository = userRepository;
    }

   /* public List<Eisen> getTasksByCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UsersDetails userDetails = (UsersDetails) authentication.getPrincipal();
        User currentUser = userDetails.getUser();
        return eisenRepository.findByUser(currentUser);
    }
**/
    public List<Eisen> getTasksByType(TaskType type) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UsersDetails userDetails = (UsersDetails) authentication.getPrincipal();
        User currentUser = userDetails.getUser();
        return eisenRepository.findByUserAndType(currentUser,type);
    }


    public Eisen save(Eisen eisen, String username) {
        User user = userRepository.findByUsername(username);
        eisen.setUser(user);
        eisen.setIsComplete(eisen.getIsComplete() != null && eisen.getIsComplete());
        if (eisen.getTaskId() == null) {
            eisen.setCreatedAt(Instant.now());
        }
        eisen.setUpdatedAt(Instant.now());
        return eisenRepository.save(eisen);
    }

    public Eisen savetaskIU (Eisen taskIU, String username) {
        User user = userRepository.findByUsername(username);
        taskIU.setUser(user);
        taskIU.setIsComplete(taskIU.getIsComplete() != null &&taskIU.getIsComplete());
        if (taskIU.getTaskId() == null) {
            taskIU.setCreatedAt(Instant.now());
        }
        taskIU.setUpdatedAt(Instant.now());
        return eisenRepository.save(taskIU);
    }




    public void delete(Eisen eisen) {
        eisenRepository.delete(eisen);}


    public Eisen findById(Long parkId) {
        return eisenRepository.findById(parkId).orElse(null);
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
