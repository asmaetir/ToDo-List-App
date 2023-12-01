package ru.maxima.libraryspringbootproject.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotEmpty;

import java.io.Serializable;
import java.time.Instant;
@Getter
@Setter
@Entity
@Table(name = "park")
public class TodoItem implements Serializable {
    @Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long parkId;

    @NotEmpty(message = "Description is required")
    private String description;

    private Boolean isComplete;

    private Instant date;
    private Instant createdAt;

    private Instant updatedAt;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Override
    public String toString() {
        return String.format("Task{parkId=%d, description='%s', isComplete='%s', createdAt='%s', date='%s', updatedAt='%s',user='%s'}",
                parkId, description, isComplete, createdAt,date, updatedAt,user);
    }
}
