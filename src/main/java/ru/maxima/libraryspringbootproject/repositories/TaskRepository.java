package ru.maxima.libraryspringbootproject.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ru.maxima.libraryspringbootproject.model.Task;
import ru.maxima.libraryspringbootproject.model.User;

import java.time.LocalDate;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@Component
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    @Autowired
    double countAllByIsCompleteIsTrue();

    Iterable<Task> findByDescriptionContainingIgnoreCase(String searchText);

   @Query("SELECT t FROM Task t WHERE t.user.username = :username AND t.description LIKE %:searchText%")
   List<Task> findByUserUsername(@Param("username") String username, @Param("searchText") String searchText);

   List<Task> findByUser(User user);

    //long countByUserUsernameAndCreatedAtBetween(String username, Instant startDate, Instant endDate);

   // long countByUserUsernameAndIsCompleteAndCreatedAtBetween(String username, boolean isComplete, Instant startDate, Instant endDate);

    long countByUser(User currentUser);

    long countByUserAndIsComplete(User currentUser, boolean isComplete);


    List<Task> findByCreatedAtBetween(Instant startDate, Instant endDate);


    long countByUserAndCreatedAtBetween(User user, LocalDateTime startDateTime, LocalDateTime endDateTime);
    long countByUserAndIsCompleteAndCreatedAtBetween(User user, boolean isComplete, LocalDateTime startDateTime, LocalDateTime endDateTime);


}

