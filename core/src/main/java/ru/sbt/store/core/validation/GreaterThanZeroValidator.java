package ru.sbt.store.core.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

public class GreaterThanZeroValidator implements ConstraintValidator<GreaterThanZero, BigDecimal> {

    @Override
    public boolean isValid(BigDecimal value, ConstraintValidatorContext context) {
        return value != null && value.compareTo(BigDecimal.valueOf(0.)) > 0;
    }
}
