package ru.maxima.libraryspringbootproject.repositories;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ru.maxima.libraryspringbootproject.model.User;

import java.util.List;
import java.util.Optional;
@Component
//@Lazy
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Autowired
    User findByUsername(String username);
    Optional<User> findUserById(int id);
    Optional<User> findByEmail(String username);

    List<User> findAll();

}
