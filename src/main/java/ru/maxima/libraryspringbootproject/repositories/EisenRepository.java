package ru.maxima.libraryspringbootproject.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ru.maxima.libraryspringbootproject.model.Eisen;
import ru.maxima.libraryspringbootproject.model.TaskType;
import ru.maxima.libraryspringbootproject.model.TodoItem;
import ru.maxima.libraryspringbootproject.model.User;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Component
@Repository

public interface EisenRepository extends JpaRepository<Eisen, Long> {
    @Autowired
  //  double countAllByIsCompleteIsTrue();
  //  Iterable<Eisen> findByDescriptionContainingIgnoreCase(String searchpark);
    List<Eisen> findByUserUsername(String username);
    List<Eisen> findByUser(User user);
   // List<Eisen> findByUserAndDate(User user, Instant createdAt);

    void delete(Eisen eisen);

    Optional<Eisen> findById(Long parkId);

    Eisen save(Eisen eisen);

   // List<Eisen> findByType(User user,TaskType type);
    List<Eisen> findByUserAndType(User user, TaskType type);


}
