package ru.sbt.store.core.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class PhoneNumberValidator implements ConstraintValidator<ValidPhoneNumber, String> {

    private static final Pattern phonePattern =
            Pattern.compile("^((\\+7)|8)\\d{10}$");


    @Override
    public boolean isValid(String phone, ConstraintValidatorContext context) {
        return phone != null && phonePattern.matcher(phone).matches();
    }
}
