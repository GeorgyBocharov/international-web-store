package ru.sbt.common.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class CurrencyDto {

    private Long id;

    private String name;

    private BigDecimal multiplier;
}
