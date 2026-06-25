package com.example.Railway_Ticket_Booking.Repos;

import com.example.Railway_Ticket_Booking.DTO.Train_response_dto;
import com.example.Railway_Ticket_Booking.Entity.Train;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface Train_details extends JpaRepository<Train,Long> {
    List<Train> findTrainBySourceAndDestination(String source, String destination);
    Optional<Train> findById(long id);

}
