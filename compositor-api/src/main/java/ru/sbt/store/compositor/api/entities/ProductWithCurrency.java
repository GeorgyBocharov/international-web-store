package ru.sbt.store.compositor.api.entities;

import ru.sbt.common.entities.CurrencyDto;
import ru.sbt.common.entities.InfoDto;
import ru.sbt.common.entities.ParameterDto;
import ru.sbt.common.entities.Product;
import lombok.*;


import java.math.BigDecimal;
import java.util.*;


@Getter
@ToString
@EqualsAndHashCode
@Builder
public class ProductWithCurrency {
    private final Long id;
    @Singular("priceInCurrency")
    private final List<PriceInCurrency> priceInCurrencies;
    private final Set<ParameterDto> parameters;
    private final InfoDto info;
    private final BigDecimal priceCU;
}
