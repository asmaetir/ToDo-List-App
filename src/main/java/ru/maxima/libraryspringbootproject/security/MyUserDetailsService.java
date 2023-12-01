package ru.maxima.libraryspringbootproject.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.maxima.libraryspringbootproject.model.User;
import ru.maxima.libraryspringbootproject.repositories.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.maxima.libraryspringbootproject.service.UserService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;



@Component
public class MyUserDetailsService implements UserDetailsService {
    private UserRepository userRepo;
    @Autowired
    public MyUserDetailsService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

      @Override
      @Transactional(readOnly = true)

      public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
          Optional<User> userRes = userRepo.findByEmail(username);
          if (userRes.isEmpty())
              throw new UsernameNotFoundException("No User found with :  " + username);
          User user = userRes.get();
          return new org.springframework.security.core.userdetails.User(
                  username,
                  user.getPassword(),
                  Collections.singletonList(new SimpleGrantedAuthority("USER")));

      }



/*


    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ru.maxima.libraryspringbootproject.model.User user = userRepo.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole())
                .build();
    }
*/
    @Transactional(readOnly = true)
    public List<User> getAll() {
        List<User> users = userRepo.findAll();
        return users;
    }
    public User save(User user) {
        return userRepo.save(user);
    }


}







