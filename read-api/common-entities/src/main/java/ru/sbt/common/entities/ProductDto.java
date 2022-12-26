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
public class ProductDto {

    private Long id;

    private BigDecimal priceCU;

    private Set<InfoDto> infoDtoSet = new HashSet<>();

    private Set<ParameterDto> parameterDtoSet = new HashSet<>();

}
