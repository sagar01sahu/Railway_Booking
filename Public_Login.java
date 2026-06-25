package com.example.Railway_Ticket_Booking.Controller;

import com.example.Railway_Ticket_Booking.DTO.*;
import com.example.Railway_Ticket_Booking.Services.JwtService;
import com.example.Railway_Ticket_Booking.Services.Ticket_booking_service;
import com.example.Railway_Ticket_Booking.Services.Train_services;
import com.example.Railway_Ticket_Booking.Services.User_login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/public")
public class Public_Login {

    @Autowired
    private User_login user_login;
    @Autowired
    private Train_services train_services;
    @Autowired
    private Ticket_booking_service ticket_booking_service;
    @Autowired
    private AuthenticationManager authenticationManager;

    // Inject the new JWT Service
    @Autowired
    private JwtService jwtService;

    @PostMapping("/getTrain")
    public List<Train_response_dto> getTrains(@RequestBody Train_finder_DTO train_finder_Dto){
        return train_services.getTrain(train_finder_Dto);
    }

    @PostMapping("/bookTicket")
    public TicketResponseDto creteNewTicket(@RequestBody TicketBookingRequestDto ticketBookingRequestDto){
        return ticket_booking_service.createTicket(ticketBookingRequestDto);
    }

    // UPDATED: Now uses LoginRequestDto and returns a JWT
    @PostMapping("/login")
    public String login(@RequestBody LoginRequestDto loginRequestDto){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDto.getUsername(),
                        loginRequestDto.getPassword()
                )
        );

        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(loginRequestDto.getUsername());
        } else {
            throw new RuntimeException("Invalid Access!");
        }
    }

    // UNCHANGED: Still requires all details for registration
    @PostMapping("/register")
    public UserResponseDto register(@RequestBody UserRequestDto request){
        return user_login.register_user(request);
    }
}