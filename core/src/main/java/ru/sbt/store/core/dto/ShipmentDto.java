package ru.sbt.store.core.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class ShipmentDto {

    private Long id;

    private long orderId;

    @Length(min = 3, max = 100)
    private String address;
}
