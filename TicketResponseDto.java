package com.example.Railway_Ticket_Booking.DTO;

import jakarta.persistence.EnumType;
import jakarta.persistence.EnumeratedValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketResponseDto {
    private String pnr;
    private String trainName;
    private String passengerName;
    private String source;
    private String destination;
    private LocalDate startDate;

    private String status;
}
