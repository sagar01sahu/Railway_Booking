package com.example.Railway_Ticket_Booking.Repos;

import com.example.Railway_Ticket_Booking.Entity.User__;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface User_details extends JpaRepository<User__,Long> {
    @Override
    Optional<User__> findById(Long aLong);
    User__ findByUsername(String username);



}
