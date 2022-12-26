package ru.sbt.common.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Product {

    private InfoDto info;
    private Set<ParameterDto> parameters = new HashSet<>();
    private BigDecimal priceCU;

    private Long id;


}
