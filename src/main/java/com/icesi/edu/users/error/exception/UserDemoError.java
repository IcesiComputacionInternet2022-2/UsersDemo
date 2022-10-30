package com.icesi.edu.users.error.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
@Getter
public class UserDemoError {
    private String code;
    private String message;
}
