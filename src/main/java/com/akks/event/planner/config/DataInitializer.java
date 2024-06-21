package com.akks.event.planner.config;

import com.akks.event.planner.model.*;
import com.akks.event.planner.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private EventService eventService;

    @Autowired
    private UserService userService;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private VenueService venueService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private OrganizerService organizerService;

    @Override
    public void run(String... args) throws Exception {
        // Create Venues
        Venue venue1 = new Venue("Convention Center", "123 Main St", 1000);
        Venue venue2 = new Venue("Open Air Theatre", "456 Park Ave", 500);
        venueService.createVenue(venue1);
        venueService.createVenue(venue2);

        // Create Categories
        Category category1 = new Category("Music", "Live music events");
        Category category2 = new Category("Technology", "Tech conferences and workshops");
        categoryService.createCategory(category1);
        categoryService.createCategory(category2);

        // Create Organizers
        Organizer organizer1 = new Organizer("John Doe", "john@example.com");
        Organizer organizer2 = new Organizer("Jane Smith", "jane@example.com");
        organizerService.createOrganizer(organizer1);
        organizerService.createOrganizer(organizer2);

        // Create Events
        Event event1 = new Event("2024-07-15", "http://example.com/image1.jpg", "Music Fest", "New York", venue1, organizer1);
        event1.setCategories(Arrays.asList(category1));
        Event event2 = new Event("2024-08-20", "http://example.com/image2.jpg", "Tech Summit", "San Francisco", venue2, organizer2);
        event2.setCategories(Arrays.asList(category2));
        eventService.createEvent(event1);
        eventService.createEvent(event2);

        // Create Users
        User user1 = new User("Alice", "alice@example.com", "password123");
        User user2 = new User("Bob", "bob@example.com", "password456");
        userService.createUser(user1);
        userService.createUser(user2);

        // Create Tickets
        Ticket ticket1 = new Ticket(event1, user1, "A1");
        Ticket ticket2 = new Ticket(event2, user2, "B2");
        ticketService.createTicket(ticket1);
        ticketService.createTicket(ticket2);

        System.out.println("Test data initialized.");
    }
}
