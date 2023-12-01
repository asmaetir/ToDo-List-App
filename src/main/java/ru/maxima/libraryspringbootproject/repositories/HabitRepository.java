package ru.maxima.libraryspringbootproject.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ru.maxima.libraryspringbootproject.model.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository

public interface HabitRepository  extends JpaRepository<Habit, Long> {


    List<Habit> findByUser(User user);
    List<Habit> findByUserAndDate(User user, LocalDate date);

    @Query("SELECT h FROM Habit h WHERE h.id = :id AND h.user.username = :username")
    Habit findByIdAndUser_Username(@Param("id") Long id, @Param("username") String username);
}

