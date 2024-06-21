package com.akks.event.planner.service;

import com.akks.event.planner.model.Event;
import com.akks.event.planner.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Optional<Event> getEventById(Long id) {
        return eventRepository.findById(id);
    }

    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }

    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }

    public Event updateEvent(Long id, Event eventDetails) {
        Optional<Event> eventOptional = eventRepository.findById(id);
        if (eventOptional.isPresent()) {
            Event event = eventOptional.get();
            event.setDate(eventDetails.getDate());
            event.setImageUrl(eventDetails.getImageUrl());
            event.setTitle(eventDetails.getTitle());
            event.setLocation(eventDetails.getLocation());
            event.setVenue(eventDetails.getVenue());
            event.setOrganizer(eventDetails.getOrganizer());
            event.setCategories(eventDetails.getCategories());
            event.setTickets(eventDetails.getTickets());
            return eventRepository.save(event);
        } else {
            throw new RuntimeException(STR."Event not found with id \{id}");
        }
    }
}
