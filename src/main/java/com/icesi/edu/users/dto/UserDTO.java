package com.icesi.edu.users.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import com.icesi.edu.users.validation.CustomAnnotations;

import javax.persistence.Column;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

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


}
