package ru.sbt.store.compositor.api.entities;

import ru.sbt.common.entities.CurrencyDto;
import ru.sbt.common.entities.InfoDto;
import ru.sbt.common.entities.ParameterDto;
import ru.sbt.common.entities.Product;
import lombok.*;


import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class ProductWithCurrency {

    private Long id;
    private Map<CurrencyDto, BigDecimal> pricesPerCurrency = new HashMap<>();
    private Set<ParameterDto> parameters = new HashSet<>();
    private InfoDto info;
    private BigDecimal priceCU;

    public ProductWithCurrency(Product product) {
        this.id = product.getId();
        this.priceCU = product.getPriceCU();
        this.parameters = product.getParameters();
        this.info = product.getInfo();
    }

    public void addPricePerCurrency(CurrencyDto currencyDto, BigDecimal price) {
        pricesPerCurrency.put(currencyDto, price);
    }
}
