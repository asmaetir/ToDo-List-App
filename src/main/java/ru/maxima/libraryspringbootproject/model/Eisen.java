package ru.maxima.libraryspringbootproject.model;




import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;


@Getter
@Setter
@Entity
@Table(name = "Eisen")
public class Eisen implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskId;

    @NotBlank(message = "Name is required")
    private String task;

    @Enumerated(EnumType.STRING)
    private TaskType type;
    @Column(name = "is_complete")

    private Boolean isComplete;

    private Instant createdAt;

    private Instant updatedAt;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Boolean getIsComplete() {
        return isComplete;
    }

    public void setIsComplete(Boolean isComplete) {
        this.isComplete = isComplete;
    }
    @Override
    public String toString() {
        return String.format("TodoItem{taskId=%d, task='%s',type='%s', is_complete='%s', createdAt='%s', updatedAt='%s',user='%s'}",
                taskId, task,type, isComplete, createdAt, updatedAt,user);
    }

}
