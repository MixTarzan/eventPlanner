package com.akks.event.planner.controller;

import com.akks.event.planner.model.*;
import com.akks.event.planner.service.VenueService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(VenueController.class)
class VenueControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VenueService venueService;

    private Venue venue1;
    private Venue venue2;

    @BeforeEach
    public void setUp() {
        venue1 = new Venue("Grand Hall", "123 Main St, Springfield", 500);
        venue2 = new Venue("akram", "55 Main St, Aarschot", 200);
    }

    @Test
    void getAllVenues() throws Exception {
        List<Venue> venues = Arrays.asList(venue1, venue2);
        when(venueService.getAllVenues()).thenReturn(venues);

        mockMvc.perform(get("/api/venues"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("Grand Hall"))
                .andExpect(jsonPath("$[1].name").value("akram"));
    }

    @Test
    void getVenueById() throws Exception {
        when(venueService.getVenueById(1L)).thenReturn(Optional.of(venue1));

        mockMvc.perform(get("/api/venues/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void createVenue() throws Exception {
        when(venueService.createVenue(any(Venue.class))).thenReturn(venue1);

        String venueJson = """
    {
        "name": "Grand Hall",
        "address": "123 Main St, Springfield",
        "capacity": 500
    }
    """;

        mockMvc.perform(post("/api/venues")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(venueJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Grand Hall"))
                .andExpect(jsonPath("$.address").value("123 Main St, Springfield"))
                .andExpect(jsonPath("$.capacity").value(500));
    }

    @Test
    void deleteVenue() throws Exception {
        doNothing().when(venueService).deleteVenue(1L);

        mockMvc.perform(delete("/api/venues/1"))
                .andExpect(status().isNoContent());

        verify(venueService).deleteVenue(1L);
    }
}