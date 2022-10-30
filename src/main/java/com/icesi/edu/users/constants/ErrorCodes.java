package com.icesi.edu.users.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCodes {
    INVALID_FORMAT("Error_01:", "Invalid format"),
    UNAUTHORIZED("Error_401:", " You must be authenticated to make this request"),
    BAD_DATA("Error_02:", " Invalid data"),
    NOT_FOUND("Error_03:", " Resource don't found");
    private String code;
    private String message;
}//End ErrorCodes
