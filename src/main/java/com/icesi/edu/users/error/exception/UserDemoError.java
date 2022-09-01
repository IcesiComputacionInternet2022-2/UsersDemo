package com.icesi.edu.users.error.exception;

import com.icesi.edu.users.constant.ErrorConstants;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDemoError {

    private ErrorConstants code;
    private String message;
}
