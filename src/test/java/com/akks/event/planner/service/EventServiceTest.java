package com.akks.event.planner.service;


import com.akks.event.planner.model.Event;
import com.akks.event.planner.repository.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EventService eventService;

    private Event event;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        event = new Event();
        event.setId(1L);
        event.setDate("2024-05-20T10:00:00");
        event.setImageUrl("https://example.com/image.jpg");
        event.setTitle("Sample Event");
        event.setLocation("Sample Location");
    }

    @Test
    void saveEvent() {
        when(eventRepository.save(any(Event.class))).thenReturn(event);

        Event savedEvent = eventService.saveEvent(event);

        assertNotNull(savedEvent);
        assertEquals(event.getId(), savedEvent.getId());
        verify(eventRepository, times(1)).save(event);
    }

    @Test
    void getEventById() {
        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));

        Optional<Event> foundEvent = eventService.getEventById(1L);

        assertTrue(foundEvent.isPresent());
        assertEquals(event.getId(), foundEvent.get().getId());
        verify(eventRepository, times(1)).findById(1L);
    }

    @Test
    void getAllEvents() {
        eventService.getAllEvents();
        verify(eventRepository, times(1)).findAll();
    }

    @Test
    void deleteEvent() {
        eventService.deleteEvent(1L);
        verify(eventRepository, times(1)).deleteById(1L);
    }
}