package com.akks.event.planner.repository;

import com.akks.event.planner.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}