package ru.maxima.libraryspringbootproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.maxima.libraryspringbootproject.model.User;
import ru.maxima.libraryspringbootproject.repositories.UserRepository;

import java.time.LocalDateTime;

@Service
public class RegistrationService {
    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationService(UserService userService, UserRepository userRepository,@Lazy PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Transactional
    public User saveRegisteredUser(User registeredUser) {
        User user = new User();
        if (registeredUser != null) {
            user.setUsername(registeredUser.getUsername());
            user.setEmail(registeredUser.getEmail());
            user.setUsername(registeredUser.getUsername());
            user.setRole("USER");
            String password = registeredUser.getPassword();
            user.setPassword(passwordEncoder.encode(password));
            userRepository.save(user);
            return user;
        } else {
            return null;
        }
    }
}
