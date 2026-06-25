package com.example.Railway_Ticket_Booking.Repos;

import com.example.Railway_Ticket_Booking.Entity.Ticket_booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Train_book extends JpaRepository<Ticket_booking,Long> {
}
