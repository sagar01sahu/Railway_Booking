package com.example.Railway_Ticket_Booking.DTO;

import jakarta.persistence.Column;
import lombok.*;

import java.time.LocalTime;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Train_request_Dto {

    private String trainNumber;

    private  String trainName;

    private String source;

    private String destination;

    private LocalTime departureTime;

    private LocalTime arrivalTime;

    private int totalSeats;
}
