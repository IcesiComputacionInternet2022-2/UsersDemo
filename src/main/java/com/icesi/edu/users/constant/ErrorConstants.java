package com.icesi.edu.users.constant;

import lombok.Getter;

@Getter
public enum ErrorConstants {

    CODE_UD_01("EMAIL OR PHONE NUMBER IS REQUIRED"),
    CODE_UD_02("EMAIL DOMAIN IS NOT VALID"),
    CODE_UD_03("EMAIL CANNOT HAVE SPECIAL CHARACTERS"),
    CODE_UD_04("PHONE EXTENSION IS NOT VALID"),
    CODE_UD_05("PHONE NUMBER CANNOT HAVE SPACES"),
    CODE_UD_06("PHONE NUMBER IS NOT VALID"),
    CODE_UD_07("FIRST AND LAST NAME CANNOT BE MORE THAN 120 CHARACTERS"),
    CODE_UD_08("FIRST AND LAST NAME CANNOT HAVE SPECIAL CHARACTERS"),
    CODE_UD_09("EMAIL OR PHONE ARE ALREADY USED");

    private final String message;

    ErrorConstants(String message) {
        this.message = message;
    }
}
