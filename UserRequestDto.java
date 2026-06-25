package com.example.Railway_Ticket_Booking.DTO;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserRequestDto {
    private  String username;
    private  String password;
    private  String email;
    private  String phone;

}
