package ru.sbt.store.core.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

@Getter
@Setter
public class PaymentDto {

    private Long id;

    private Long orderId;

    private Long currencyId;

    @Pattern(regexp = "^\\d{16}$")
    private String cardPan;

    private BigDecimal value;

    @Valid
    private CurrencyDto currencyDto;

}
