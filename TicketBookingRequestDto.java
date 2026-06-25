package com.example.Railway_Ticket_Booking.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketBookingRequestDto {
    private Long UserId;
    private Long TrainId;
    private String passengerName;
    private int age;
    private LocalDate startDate;

}
