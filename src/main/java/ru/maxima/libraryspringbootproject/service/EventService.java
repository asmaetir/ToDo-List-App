package ru.maxima.libraryspringbootproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Service;
import ru.maxima.libraryspringbootproject.model.Event;
import ru.maxima.libraryspringbootproject.model.User;
import ru.maxima.libraryspringbootproject.repositories.EventRepository;
import ru.maxima.libraryspringbootproject.repositories.UserRepository;
import ru.maxima.libraryspringbootproject.security.UsersDetails;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;
    private UserRepository userRepository;

    @Autowired
    public EventService(@Lazy EventRepository eventRepository, @Lazy UserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }


    public Event save(Event event, String username) {
        User user = userRepository.findByUsername(username);
        event.setUser(user);

        return eventRepository.save(event);
    }

    public List<Event>searchEventsByUsername(String username,String searchevent) {
        List<Event> events = eventRepository.findByUsername(username,searchevent);
        return events;
    }

    public List<Event> getTasksByCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UsersDetails userDetails = (UsersDetails) authentication.getPrincipal();
        User currentUser = userDetails.getUser();
        return eventRepository.findByUser(currentUser);
    }

    public void delete(Event event) {
        eventRepository.delete(event);
    }



    public Optional<Event> getById(Long id) {
        return eventRepository.findById(id);
    }




    public Event findById(Long id) {return eventRepository.findById(id).orElse(null);}


    /*public List<Event> searchEventsByUsername(String username, String searchevent) {
        return eventRepository.findByUserUsername(username, searchevent);

    }*/
}



