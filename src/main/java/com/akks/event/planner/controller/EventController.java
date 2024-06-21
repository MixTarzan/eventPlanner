package com.akks.event.planner.controller;


import com.akks.event.planner.model.Event;
import com.akks.event.planner.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable Long id) {
        return eventService.getEventById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Event createEvent(@RequestBody Event event) {
        return eventService.createEvent(event);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Event> updateEvent(@PathVariable Long id, @RequestBody Event event) {
        return eventService.getEventById(id)
                .map(existingEvent -> {
                    existingEvent.setDate(event.getDate());
                    existingEvent.setImageUrl(event.getImageUrl());
                    existingEvent.setTitle(event.getTitle());
                    existingEvent.setLocation(event.getLocation());
                    eventService.updateEvent(existingEvent.getId(), existingEvent);
                    return ResponseEntity.ok(existingEvent);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        return eventService.getEventById(id)
                .map(event -> {
                    eventService.deleteEvent(id);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
