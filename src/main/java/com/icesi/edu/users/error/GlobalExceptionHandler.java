package com.icesi.edu.users.error;

import com.icesi.edu.users.error.exception.UserDemoError;
import com.icesi.edu.users.error.exception.UserDemoException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
public class GlobalExceptionHandler {

    public ResponseEntity<UserDemoError> handleException(UserDemoException userDemoException){
        return new ResponseEntity<>(userDemoException.getError(),userDemoException.getHattpeStatus());
    }
}
