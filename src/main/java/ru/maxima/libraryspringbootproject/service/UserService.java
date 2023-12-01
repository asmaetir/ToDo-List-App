package ru.maxima.libraryspringbootproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.maxima.libraryspringbootproject.model.Task;
import ru.maxima.libraryspringbootproject.model.User;
import ru.maxima.libraryspringbootproject.repositories.UserRepository;

import java.util.Collections;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public UserService( UserRepository userRepository, @Lazy PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User findUser(int id) {
        User user = userRepository.findUserById(id).orElse(null);
        if (user != null) {
            return user;
        }
        return null;
    }

    @Transactional
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

//    @Transactional
//    public void saveCreatedPerson(Person person) {
//        if (person != null) {
//            person.setCreatedPerson(getUserName());
//            person.setCreatedAt(LocalDateTime.now());
//            person.setRole("USER");
//            peopleRepository.save(person);
//        } else {
//            throw new NullPointerException("Null pointer exception");
//        }
//    }

    public String getUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        return userName;
    }


    @Transactional
    public String findRoleByName(){
        String userName = getUserName();
        User person = userRepository.findByEmail(userName ).orElse(null);
        if(person == null) return null;
        return person.getRole();
    }



    @Transactional
    public void savePUser(User user) {
        userRepository.save(user);
    }

    @Transactional
    public List<Task> getUserTasks() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username);
        if (user != null) {
            return user.getTasks();
        }
        return Collections.emptyList();
    }

    @Transactional
    public int getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username);
        if (user != null) {
            return user.getId();
        }
        return 0;
    }


/*
    public User getUserByUsername(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        return userOptional.orElse(null); // Si aucun utilisateur correspondant n'est trouvé, retourne null
    }*/

}

