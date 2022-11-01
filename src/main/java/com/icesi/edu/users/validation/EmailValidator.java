package com.icesi.edu.users.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class EmailValidator implements ConstraintValidator<CustomAnnotations.EmailValidation, String> {

    @Override
    public void initialize(CustomAnnotations.EmailValidation emailValidation) {    }

    @Override
    public boolean isValid(String str, ConstraintValidatorContext constraintValidatorContext) {
        return Pattern.compile(".*(@ICESI\\.EDU\\.CO)$|.*(@icesi\\.edu\\.co)$").matcher(str).find();
    }
}