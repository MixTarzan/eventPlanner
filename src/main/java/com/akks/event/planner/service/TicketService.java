package com.akks.event.planner.service;

import com.akks.event.planner.model.Ticket;
import com.akks.event.planner.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TicketService {
    @Autowired
    private TicketRepository ticketRepository;

    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    public Optional<Ticket> getTicketById(Long id) {
        return ticketRepository.findById(id);
    }

    public Ticket createTicket(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    public void deleteTicket(Long id) {
        ticketRepository.deleteById(id);
    }

    public Ticket updateTicket(Long id, Ticket ticketDetails) {
        Optional<Ticket> ticketOptional = ticketRepository.findById(id);
        if (ticketOptional.isPresent()) {
            Ticket ticket = ticketOptional.get();
            ticket.setEvent(ticketDetails.getEvent());
            ticket.setUser(ticketDetails.getUser());
            ticket.setSeatNumber(ticketDetails.getSeatNumber());
            return ticketRepository.save(ticket);
        } else {
            throw new RuntimeException(STR."Ticket not found with id \{id}");
        }
    }
}
