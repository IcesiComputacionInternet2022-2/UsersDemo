package com.icesi.edu.users.dto;

<<<<<<< Updated upstream
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
=======
import com.icesi.edu.users.validation.CustomAnnotations;
import lombok.*;

import javax.persistence.Column;
import java.time.LocalDate;
>>>>>>> Stashed changes
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private UUID id;

<<<<<<< Updated upstream
    private String email;

    private String phoneNumber;

    private String firstName;

    private String lastName;

    private LocalDate lastTimeSearched;
=======
    @Column(unique = true)
    @CustomAnnotations.EmailValidation
    private String email;

    @Column(unique = true)
    @CustomAnnotations.PhoneValidation
    private String phoneNumber;

    @CustomAnnotations.NameValidation
    private String firstName;

    @CustomAnnotations.NameValidation
    private String lastName;

    @CustomAnnotations.PasswordValidation
    private String password;

    private LocalDate lastTimeSearched;


>>>>>>> Stashed changes
}
