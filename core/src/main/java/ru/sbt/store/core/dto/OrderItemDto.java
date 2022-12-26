package ru.sbt.store.core.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@Getter
@Setter
public class OrderItemDto {

    private Long id;

    private Long productId;

    private Long orderId;

    @Min(1)
    private int quantity;

    @Valid
    private ProductDto productDto;
}
