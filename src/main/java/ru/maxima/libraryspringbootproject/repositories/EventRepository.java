package ru.maxima.libraryspringbootproject.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ru.maxima.libraryspringbootproject.model.Event;
import ru.maxima.libraryspringbootproject.model.Task;
import ru.maxima.libraryspringbootproject.model.User;

import java.time.LocalDate;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
@Component
@Repository
public interface EventRepository extends JpaRepository < Event, Long> {
    @Autowired

    List<Event> findByUser(User user);

    @Query("SELECT t FROM Event t WHERE t.user.username = :username AND t.name LIKE %:searchevent%")
    List<Event> findByUsername(@Param("username") String username, @Param("searchevent") String searchevent);

    //List<Event> findByUserUsername(String username);




}