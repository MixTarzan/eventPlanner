package com.akks.event.planner.controller;

import com.akks.event.planner.model.Event;
import com.akks.event.planner.service.EventService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@WebMvcTest(EventController.class)
class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EventService eventService;

    private Event event;

    @BeforeEach
    void setUp() {
        event = new Event();
        event.setId(1L);
        event.setDate("2024-05-20");
        event.setImageUrl("https://example.com/image.jpg");
        event.setTitle("Sample Event");
        event.setLocation("Sample Location");
    }

    @Test
    void createEvent() throws Exception {
        when(eventService.saveEvent(any(Event.class))).thenReturn(event);

        mockMvc.perform(MockMvcRequestBuilders.post("/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"date\":\"2024-05-20\",\"imageUrl\":\"https://example.com/image.jpg\",\"title\":\"Sample Event\",\"location\":\"Sample Location\"}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L));
    }

    @Test
    void getAllEvents() throws Exception {
        when(eventService.getAllEvents()).thenReturn(Arrays.asList(event));

        mockMvc.perform(MockMvcRequestBuilders.get("/events")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1L));
    }

    @Test
    void getEventById() throws Exception {
        when(eventService.getEventById(1L)).thenReturn(Optional.of(event));

        mockMvc.perform(MockMvcRequestBuilders.get("/events/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L));
    }

    @Test
    void updateEvent() throws Exception {
        when(eventService.getEventById(1L)).thenReturn(Optional.of(event));

        mockMvc.perform(MockMvcRequestBuilders.put("/events/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"date\":\"2024-05-20\",\"imageUrl\":\"https://example.com/image.jpg\",\"title\":\"Sample Event\",\"location\":\"Sample Location\"}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L));
    }

    @Test
    void deleteEvent() throws Exception {
        when(eventService.getEventById(1L)).thenReturn(Optional.of(event));

        mockMvc.perform(MockMvcRequestBuilders.delete("/events/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(eventService).deleteEvent(1L);
    }
}
