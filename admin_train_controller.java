package com.example.Railway_Ticket_Booking.Controller;

import com.example.Railway_Ticket_Booking.DTO.Train_finder_DTO;
import com.example.Railway_Ticket_Booking.DTO.Train_request_Dto;
import com.example.Railway_Ticket_Booking.DTO.Train_response_dto;
import com.example.Railway_Ticket_Booking.Entity.Ticket_booking;
import com.example.Railway_Ticket_Booking.Entity.Train;
import com.example.Railway_Ticket_Booking.Repos.Train_book;
import com.example.Railway_Ticket_Booking.Services.Ticket_booking_service;
import com.example.Railway_Ticket_Booking.Services.Train_services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class admin_train_controller {

    @Autowired
    private Train_services train_services;
    @Autowired
    private Ticket_booking_service ticket_booking_service;

    @PostMapping("/createTrain")
    public String createTrain(@RequestBody Train_request_Dto train_request_Dto){
        return train_services.createTrain(train_request_Dto);
    }

    // Changed to @PostMapping and wrapped return type in List
    @PostMapping("/getTrain")
    public List<Train_response_dto> getTrains(@RequestBody Train_finder_DTO train_finder_Dto){
        return train_services.getTrain(train_finder_Dto);
    }
    @GetMapping("/allTrains")
    public List<Train> getAlltrains(){
        return train_services.getAllTrains();
    }
    @GetMapping("/allTickets")
    public List<Ticket_booking>  getAllTickets(){
        return ticket_booking_service.getAllTicket();
    }
}
