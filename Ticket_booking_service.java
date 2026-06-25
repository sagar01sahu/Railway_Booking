package com.example.Railway_Ticket_Booking.Services;

import com.example.Railway_Ticket_Booking.DTO.TicketBookingRequestDto;
import com.example.Railway_Ticket_Booking.DTO.TicketResponseDto;
import com.example.Railway_Ticket_Booking.Entity.BookingStatus;
import com.example.Railway_Ticket_Booking.Entity.Ticket_booking;
import com.example.Railway_Ticket_Booking.Entity.Train;
import com.example.Railway_Ticket_Booking.Entity.User__;
import com.example.Railway_Ticket_Booking.Repos.Train_book;
import com.example.Railway_Ticket_Booking.Repos.Train_details;
import com.example.Railway_Ticket_Booking.Repos.User_details;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class Ticket_booking_service {

    private final Train_book train_book;
    private final Train_details train_details;
    private final User_details user_details;

    @Transactional
    public TicketResponseDto createTicket(TicketBookingRequestDto requestDto) {

        if (requestDto.getUserId() == null || requestDto.getTrainId() == null) {
            throw new IllegalArgumentException("User ID and Train ID cannot be missing.");
        }

        User__ user = user_details.findById(requestDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Train train = train_details.findById(requestDto.getTrainId())
                .orElseThrow(() -> new RuntimeException("Train not found"));

        // NEW LOGIC: Stop them immediately if seats are already gone
        if (train.getAvailableSeats() <= 0) {
            throw new RuntimeException("Sorry! This train is fully booked.");
        }

        try {
            // 1. Decrement the seats
            train.setAvailableSeats(train.getAvailableSeats() - 1);

            // 2. FORCE the database update right now so we can check the Version
            train_details.saveAndFlush(train);

            // 3. Create the ticket only if the train update succeeded
            Ticket_booking ticket_booking = new Ticket_booking();
            ticket_booking.setPnr(generateRandomPnr());
            ticket_booking.setUser(user);
            ticket_booking.setTrain(train);
            ticket_booking.setPassengerName(requestDto.getPassengerName());
            ticket_booking.setPassengerAge(requestDto.getAge());
            ticket_booking.setJourneyDate(requestDto.getStartDate());
            ticket_booking.setStatus(BookingStatus.CONFIRMED);
            ticket_booking.setBookingDate(LocalDateTime.now());
            train_book.save(ticket_booking);

            // 4. Return DTO
            TicketResponseDto ticketResponseDto = new TicketResponseDto();
            ticketResponseDto.setPnr(ticket_booking.getPnr());
            ticketResponseDto.setTrainName(train.getTrainName());
            ticketResponseDto.setPassengerName(requestDto.getPassengerName());
            ticketResponseDto.setSource(train.getSource());
            ticketResponseDto.setDestination(train.getDestination());
            ticketResponseDto.setStartDate(requestDto.getStartDate());
            ticketResponseDto.setStatus(ticket_booking.getStatus().toString());

            return ticketResponseDto;

        } catch (ObjectOptimisticLockingFailureException e) {
            // If another thread updated the train while we were doing the steps above!
            throw new RuntimeException("High Traffic Alert: The seat you were trying to book was just taken by someone else! Please try again.");
        }
    }

    private String generateRandomPnr() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 10).toUpperCase();
    }
    public List<Ticket_booking> getAllTicket(){
        List<Ticket_booking> ticket_bookings = train_book.findAll();
        return ticket_bookings;
    }
}
