package com.icesi.edu.users.root.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDemoError {

    private String code;
    private String message;
}
