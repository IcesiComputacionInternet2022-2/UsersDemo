package com.icesi.edu.users.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.UUID;

@Data
@AllArgsConstructor
public class UserDTO {

    private UUID id;

    private String email;

    private String phoneNumber;

    private String firstName;

    private String lastName;

    private LocalTime localTime;

    public UserDTO() {
        localTime = LocalTime.now();
    }
}
