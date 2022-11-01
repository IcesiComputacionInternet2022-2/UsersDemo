package com.icesi.edu.users.root;

import com.icesi.edu.users.root.exception.UserDemoError;
import com.icesi.edu.users.root.exception.UserDemoException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class GlobalExceptionHandler{

    public ResponseEntity<UserDemoError>handlerException(UserDemoException userDemoException) {
        return new ResponseEntity<>(userDemoException.getUserDemoError(), userDemoException.getHttpStatus());
    }
}
