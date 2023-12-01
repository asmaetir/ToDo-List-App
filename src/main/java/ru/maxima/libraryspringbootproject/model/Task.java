package ru.maxima.libraryspringbootproject.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;


@Getter
@Setter
@Entity
@Table(name = "Task")
public class Task implements Serializable {


    //@GeneratedValue(strategy = GenerationType.AUTO)

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Description is required")
    private String description;
    private int progress;

    private Boolean isComplete;
    @Temporal(TemporalType.TIMESTAMP)

    private LocalDateTime createdAt;
    @Temporal(TemporalType.TIMESTAMP)
   private Date endTime;
    private Instant updatedAt;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Override
    public String toString() {
        return String.format("Task{id=%d, description='%s', isComplete='%s', createdAt='%s',endTime='%s', updatedAt='%s',user='%s'}",
                id, description, isComplete, createdAt,createdAt,updatedAt,user);
    }

}
