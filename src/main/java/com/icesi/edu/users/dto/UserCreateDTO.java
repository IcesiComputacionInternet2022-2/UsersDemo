package com.icesi.edu.users.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.icesi.edu.users.validations.CustomAnnotations;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateDTO {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private UUID id;

    private String email;

    @CustomAnnotations.PasswordValidation
    private String password;

    private String phoneNumber;

    private String firstName;

    private String lastName;

}
