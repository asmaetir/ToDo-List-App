package ru.maxima.libraryspringbootproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.maxima.libraryspringbootproject.model.Event;
import ru.maxima.libraryspringbootproject.model.EventEntity;
import ru.maxima.libraryspringbootproject.repositories.UserRepository;
import ru.maxima.libraryspringbootproject.service.EventService;
import ru.maxima.libraryspringbootproject.service.TaskService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/auth")
public class CalendarController {
    @Autowired
    private EventService eventService;
    private UserRepository userRepository;
    private TaskService taskService;
    @Autowired
    public CalendarController(EventService eventService) {
        this.eventService = eventService;
    }
    public CalendarController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/calendar")
    public String showCalendar() {
        return "auth/calendar";
    }

    @GetMapping("/events")
    @ResponseBody
   public List<EventEntity> getEvents() {
        List<Event> events = eventService.getTasksByCurrentUser();
        List<EventEntity> eventEntities = new ArrayList<>();
        for (Event event : events) {
            EventEntity eventEntity = new EventEntity();
            eventEntity.setId(event .getId());
            eventEntity.setTitle(event .getDescription());
            eventEntity.setStart(event .getStartDay());
            eventEntity.setEnd(event .getEndDay());
            eventEntities.add(eventEntity);
        }

        return eventEntities;
    }

    }



