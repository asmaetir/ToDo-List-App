package ru.maxima.libraryspringbootproject.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ru.maxima.libraryspringbootproject.model.Task;
import ru.maxima.libraryspringbootproject.model.TodoItem;
import ru.maxima.libraryspringbootproject.model.User;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;



@Component
@Repository
public interface ParkRepository extends JpaRepository<TodoItem, Long> {
    @Autowired
    double countAllByIsCompleteIsTrue();
    Iterable<TodoItem> findByDescriptionContainingIgnoreCase(String searchText);
    List<TodoItem> findByUserUsername(String username);
    List<TodoItem> findByUser(User user);
    List<TodoItem> findByUserAndDate(User user, Instant date);
}