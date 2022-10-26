package com.icesi.edu.users.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
//@NoArgsConstructor
public class AnimalDTO {

    private UUID id;

    private String email;

    private String phoneNumber;

    private String firstName;

    private String lastName;

    private LocalDateTime dateCalled;

    public AnimalDTO(){
        dateCalled = LocalDateTime.now();
    }
}
