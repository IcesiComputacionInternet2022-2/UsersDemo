package com.icesi.edu.users.error.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class UserDemoError {
    private String code;
    private String message;
}
