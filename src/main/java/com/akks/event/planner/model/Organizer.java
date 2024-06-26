package com.akks.event.planner.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Organizer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String contact;
    @OneToMany(mappedBy = "organizer")
    private List<Event> events;

    public Organizer() {}

    public Organizer(String name, String contact) {
        this.name = name;
        this.contact = contact;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
