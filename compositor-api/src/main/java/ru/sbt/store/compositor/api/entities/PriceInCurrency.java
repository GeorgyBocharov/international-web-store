package ru.sbt.store.compositor.api.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import ru.sbt.common.entities.CurrencyDto;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Getter
@ToString
public class PriceInCurrency {
    private final CurrencyDto currency;
    private final BigDecimal price;
}
