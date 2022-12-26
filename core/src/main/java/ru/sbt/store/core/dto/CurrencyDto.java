package ru.sbt.store.core.dto;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import ru.sbt.store.core.validation.GreaterThanZero;

import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

@Getter
@Setter
public class CurrencyDto {

    private Long id;

    @Pattern(regexp = "^[ _a-zA-Z]+")
    @Length(min = 3, max = 50)
    private String name;

    @GreaterThanZero
    private BigDecimal multiplier;
}
