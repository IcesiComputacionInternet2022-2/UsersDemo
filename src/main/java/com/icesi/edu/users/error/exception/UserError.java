package com.icesi.edu.users.error.exception;

import com.icesi.edu.users.constant.UserErrorCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
@Getter
public class UserError {

    private UserErrorCode code;
    private String message;

}
