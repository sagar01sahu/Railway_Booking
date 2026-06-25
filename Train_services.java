package com.example.Railway_Ticket_Booking.Services;

import com.example.Railway_Ticket_Booking.DTO.Train_finder_DTO;
import com.example.Railway_Ticket_Booking.DTO.Train_request_Dto;
import com.example.Railway_Ticket_Booking.DTO.Train_response_dto;
import com.example.Railway_Ticket_Booking.Entity.Train;
import com.example.Railway_Ticket_Booking.Repos.Train_details;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class Train_services {

    @Autowired
    private Train_details train_details;
    @CacheEvict(value = "trains", allEntries = true)
    public String createTrain(Train_request_Dto train) {
        Train t = new Train();
        t.setTrainName(train.getTrainName());
        t.setTrainNumber(train.getTrainNumber());
        t.setSource(train.getSource());
        t.setDestination(train.getDestination());
        t.setArrivalTime(train.getArrivalTime());
        t.setDepartureTime(train.getDepartureTime());
        t.setTotalSeats(train.getTotalSeats());
        t.setAvailableSeats(train.getTotalSeats());
        train_details.save(t);
        return "Train created";
    }

    // Changed return type to List<Train_response_dto>
    @Cacheable(value = "trains", key = "#train.source + '-' + #train.destination")
    public List<Train_response_dto> getTrain(Train_finder_DTO train) {
        System.out.println("Fetching data from the slow SQL Database...");
        String source = train.getSource();
        String destination = train.getDestination();

        List<Train_response_dto> responseList = new ArrayList<>();
        List<Train> trains = train_details.findTrainBySourceAndDestination(source, destination);

        // Map each train entity to a response DTO
        for (Train t : trains) {
            Train_response_dto r = new Train_response_dto();
            r.setTrainNumber(t.getTrainNumber());
            r.setTrainName(t.getTrainName());
            r.setArrivalTime(t.getArrivalTime());
            r.setDepartureTime(t.getDepartureTime());
            r.setAvailableSeats(t.getAvailableSeats());
            responseList.add(r);
        }

        return responseList;
    }
    public List<Train> getAllTrains() {
        List<Train> trains = train_details.findAll();
        return trains;
    }
}
