package ru.maxima.libraryspringbootproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.maxima.libraryspringbootproject.model.Event;
import ru.maxima.libraryspringbootproject.repositories.UserRepository;
import ru.maxima.libraryspringbootproject.security.UsersDetails;
import ru.maxima.libraryspringbootproject.service.EventService;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/auth")
public class EventController {
    @Autowired

    private EventService eventService;


    private UserRepository userRepository;

    public EventController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }


    @GetMapping("/createvent")
    public String showCreateTaskForm(Model model) {
        model.addAttribute("event", new Event());
        return "auth/new-event";
    }

    @PostMapping("/savevent")
    public String addTask(@ModelAttribute("event") Event event, Authentication authentication,
                          @RequestParam("startDay") String startDayString,
                          @RequestParam("endDay") String endDayString) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();


        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date startDay = null;
        Date endDay = null;
        try {
            startDay = dateFormat.parse(startDayString);
            endDay = dateFormat.parse(endDayString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        event.setStartDay(startDay);
        event.setEndDay(endDay);

        eventService.save(event, username);

        return "redirect:/auth/listevent";
    }

    @PostMapping("/searchevent")
    public String searchEvents(@RequestParam("searchevent") String searchevent, Authentication authentication, Model model) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        List<Event> filteredEvents = eventService.searchEventsByUsername(username,searchevent);
        model.addAttribute("events", filteredEvents);
        model.addAttribute("username", username);
        return "auth/listevent";
    }


    @GetMapping("/editevent/{id}")
    public String showUpdateeventForm(@PathVariable("id") Long id, Model model) {
        Event updatedEvent = eventService.getById(id)
                .orElseThrow(() -> new IllegalArgumentException("Event id: " + id + " not found"));

        model.addAttribute("event", updatedEvent );
        return "auth/edit-event";
    }

    @GetMapping("/listevent")

    public String getUserTasks(@AuthenticationPrincipal UsersDetails userDetails, Model model) {
        List<Event> events = eventService.getTasksByCurrentUser();
        model.addAttribute("events", events);
        return "auth/listevent";
    }

    @PostMapping("/dotoevent/{id}")
    public String updateEvent(@PathVariable("id") Long id, @ModelAttribute("event") Event updatedTask, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        Event event= eventService.getById(id)
                .orElseThrow(() -> new IllegalArgumentException("Event id: " + id + " not found"));

        event.setDescription(updatedTask.getDescription());
        event.setName(updatedTask.getName());
        event.setStartDay(updatedTask.getStartDay());
        event.setEndDay(updatedTask.getEndDay());


        eventService.save(event, username);

        return "redirect:/auth/listevent";
    }



    @GetMapping("/deletevent/{id}")
    public String deleteevent(@PathVariable("id") Long id, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        Event event= eventService.findById(id);

        if (event != null && event.getUser().getUsername().equals(username)) {
            eventService.delete(event);
        }

        return "redirect:/auth/listevent";
    }
}





