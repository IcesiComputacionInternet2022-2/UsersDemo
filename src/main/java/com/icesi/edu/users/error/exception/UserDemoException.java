package com.icesi.edu.users.error.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class UserDemoException {

    private HttpStatus httpStatus;
    private UserDemoError error;

}
