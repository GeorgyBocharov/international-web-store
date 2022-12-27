package ru.sbt.store.core.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Getter
public class CreateOrderDto {
    private final long clientId;
    private final String currencyName;
    private final List<CreateOrderItemDto> items;
    private final ShipmentDto shipment;
    private final PaymentDto payment;
}
