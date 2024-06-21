package com.akks.event.planner.controller;

import com.akks.event.planner.model.Organizer;
import com.akks.event.planner.service.OrganizerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/organizers")
public class OrganizerController {
    @Autowired
    private OrganizerService organizerService;

    @GetMapping
    public List<Organizer> getAllOrganizers() {
        return organizerService.getAllOrganizers();
    }

    @GetMapping("/{id}")
    public Optional<Organizer> getOrganizerById(@PathVariable Long id) {
        return organizerService.getOrganizerById(id);
    }

    @PostMapping
    public Organizer createOrganizer(@RequestBody Organizer organizer) {
        return organizerService.createOrganizer(organizer);
    }

    @DeleteMapping("/{id}")
    public void deleteOrganizer(@PathVariable Long id) {
        organizerService.deleteOrganizer(id);
    }
}
