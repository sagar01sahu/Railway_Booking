package com.example.Railway_Ticket_Booking.Services;

import com.example.Railway_Ticket_Booking.Entity.CustomUserDetails;
import com.example.Railway_Ticket_Booking.Entity.User__;
import com.example.Railway_Ticket_Booking.Repos.User_details;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private User_details user_details;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User__ user=user_details.findByUsername(username);
        if(user==null){
            throw new UsernameNotFoundException("User not find with this "+username);
        }
        return new CustomUserDetails(user);
    }
}
