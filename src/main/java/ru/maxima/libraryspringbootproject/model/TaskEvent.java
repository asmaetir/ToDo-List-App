package ru.maxima.libraryspringbootproject.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
@Getter
@Setter

public class TaskEvent implements Serializable {

    private Long id;
    private String title;
    private Date start;
    private Date end;


}
