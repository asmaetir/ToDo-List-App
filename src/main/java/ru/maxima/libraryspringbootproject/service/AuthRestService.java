package ru.maxima.libraryspringbootproject.service;

import io.jsonwebtoken.Jwt;
import org.springframework.transaction.annotation.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.maxima.libraryspringbootproject.model.User;
import ru.maxima.libraryspringbootproject.repositories.UserRepository;

@Service
@AllArgsConstructor
public class AuthRestService {
    private final UserRepository userRepository;
   /* @Transactional(readOnly = true)

    public User getCurrentUser() {

        System.out.println(SecurityContextHolder.
                getContext().getAuthentication().getPrincipal());
        Jwt principal = (Jwt) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(principal.getSubject())

                .orElseThrow(() -> new UsernameNotFoundException("User name not found - " + principal.getSubject()));
    }*/
}
