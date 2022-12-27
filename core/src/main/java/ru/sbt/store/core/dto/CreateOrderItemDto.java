package ru.sbt.store.core.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Min;

@RequiredArgsConstructor
@Getter
public class CreateOrderItemDto {
    private final long productId;
    @Min(1)
    private final int quantity;
}
