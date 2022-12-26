package ru.sbt.store.core.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
public class OrderDto {

    private Long id;

    private Long currencyId;

    private Long clientId;

    @Min(0)
    private BigDecimal cost;

    @Valid
    private Set<OrderItemDto> orderItemDtoSet;

    @Valid
    private PaymentDto paymentDto;

    @Valid
    private ShipmentDto shipmentDto;

    @Valid
    private CurrencyDto currencyDto;
}
