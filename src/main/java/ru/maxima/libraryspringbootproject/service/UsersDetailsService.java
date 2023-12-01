package ru.maxima.libraryspringbootproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.maxima.libraryspringbootproject.model.User;
import ru.maxima.libraryspringbootproject.security.UsersDetails;

@Service
public class UsersDetailsService implements UserDetailsService {
    private final UserService userService;
    @Autowired
    public UsersDetailsService(UserService userService) {
        this.userService = userService;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByEmail(username);

        if (user == null )
            throw new UsernameNotFoundException("User not found!");

        return new UsersDetails(user);
    }
}





/*
package ru.maxima.libraryspringbootproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.maxima.libraryspringbootproject.model.User;
import ru.maxima.libraryspringbootproject.repositories.UserRepository;
import ru.maxima.libraryspringbootproject.security.UsersDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class UsersDetailsService implements UserDetailsService {
    private final UserService userService;
    private UserRepository userRepository;

    @Autowired
    public UsersDetailsService(UserService userService) {
        this.userService = userService;
    }

   ---------@Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userService.findByEmail(email);

        if (user == null )
            throw new UsernameNotFoundException("User not found!");

        return new UsersDetails(user);
    }------------
         override
public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userService.findByUsername(username);
    if (user == null) {
        throw new UsernameNotFoundException("User not found");
    }
    // Get the user's authorities (roles) and create the UserDetailsImpl object
    Collection<GrantedAuthority> authorities = getAuthorities(user);
    return new UsersDetails(user.getUsername(), user.getPassword(), authorities);
}

    private Collection<GrantedAuthority> getAuthorities(User user) {
        List<GrantedAuthority> authorities = new ArrayList<>();

        // Add authorities (roles) based on the user's role property
        authorities.add(new SimpleGrantedAuthority(user.getRole()));

        // You can also add additional authorities based on your application's logic
        // For example, if a user has multiple roles, you can iterate over them and add each role as an authority

        return authorities;        // You can use your own logic to map roles to GrantedAuthority objects
    }
}

*/