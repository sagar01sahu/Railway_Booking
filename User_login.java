package com.example.Railway_Ticket_Booking.Services;

import com.example.Railway_Ticket_Booking.DTO.UserRequestDto;
import com.example.Railway_Ticket_Booking.DTO.UserResponseDto;
import com.example.Railway_Ticket_Booking.Entity.User__;
import com.example.Railway_Ticket_Booking.Repos.User_details;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
public class User_login {
    @Autowired
    private User_details user_details;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    public UserResponseDto register_user(UserRequestDto userRequestDto){
        User__ user=new User__();
        user.setUsername(userRequestDto.getUsername());
        String password=bCryptPasswordEncoder.encode(userRequestDto.getPassword());
        user.setPassword(password);
        user.setEmail(userRequestDto.getEmail());
        user.setPhone(userRequestDto.getPhone());
        user.setCreateAt(LocalTime.now());
        user.setRole("ROLE_CLIENT");
        User__ saveduser=user_details.save(user);
        UserResponseDto userResponseDto=new UserResponseDto();
        userResponseDto.setId(saveduser.getId());
        userResponseDto.setUsername(saveduser.getUsername());
        return  userResponseDto;
    }

}
