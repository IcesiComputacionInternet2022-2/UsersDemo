package com.icesi.edu.users.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
public class UserDemoException extends RuntimeException {

    private HttpStatus httpStatus;
    private UserDemoError error;
}
