package com.example.Railway_Ticket_Booking.DTO;

import lombok.*;

import java.io.Serializable;
import java.time.LocalTime;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Train_response_dto implements Serializable {
    private String trainNumber;

    private  String trainName;
    private LocalTime departureTime;

    private LocalTime arrivalTime;
    private int availableSeats;
}
