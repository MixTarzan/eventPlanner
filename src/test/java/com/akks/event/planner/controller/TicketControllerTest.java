package com.akks.event.planner.controller;

import com.akks.event.planner.model.*;
import com.akks.event.planner.service.TicketService;
import com.akks.event.planner.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(TicketController.class)
class TicketControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TicketService ticketService;

    private Ticket ticket1;
    private Ticket ticket2;

    @BeforeEach
    public void setUp() {
        User user = new User("Alice", "alice@example.com", "password123", UserRole.USER);
        Venue venue1 = new Venue("Grand Hall", "123 Main St, Springfield", 500);
        Organizer organizer1 = new Organizer("Event Masters", "info@eventmasters.com, 555-1234");

        Event event = new Event(
                "2024-09-15",
                "https://example.com/images/event1.jpg",
                "Autumn Music Festival",
                "Springfield",
                venue1,
                organizer1
        );

        ticket1 = new Ticket(event, user,"B1");
        ticket2 = new Ticket(event, user,"B2");
    }

    @Test
    void getAllTickets() throws Exception {
        List<Ticket> tickets = Arrays.asList(ticket1, ticket2);
        when(ticketService.getAllTickets()).thenReturn(tickets);

        mockMvc.perform(get("/api/tickets"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].seatNumber").value("B1"))
                .andExpect(jsonPath("$[1].seatNumber").value("B2"));
    }

    @Test
    void getTicketById() throws Exception {
        when(ticketService.getTicketById(1L)).thenReturn(Optional.of(ticket1));

        mockMvc.perform(get("/api/tickets/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void createTicket() throws Exception {
        when(ticketService.createTicket(any(Ticket.class))).thenReturn(ticket1);

        String ticketJson = """
    {
        "event": {
            "date": "2024-09-15",
            "title": "Autumn Music Festival"
        },
        "user": {
            "name": "Alice",
            "email": "alice@example.com"
        },
        "seatNumber": "B1"
    }
    """;

        mockMvc.perform(post("/api/tickets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ticketJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.seatNumber").value("B1"))
                .andExpect(jsonPath("$.event.title").value("Autumn Music Festival"))
                .andExpect(jsonPath("$.user.name").value("Alice"));
    }

    @Test
    void deleteTicket() throws Exception {
        doNothing().when(ticketService).deleteTicket(1L);

        mockMvc.perform(delete("/api/tickets/1"))
                .andExpect(status().isNoContent());

        verify(ticketService).deleteTicket(1L);
    }
}