package ru.sbt.store.core.dto;

import lombok.Getter;
import lombok.Setter;
import ru.sbt.store.core.validation.GreaterThanZero;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
public class ProductDto {

    private Long id;

    @GreaterThanZero
    private BigDecimal priceCU;

    @Valid
    private Set<InfoDto> infoDtoSet;

    @Valid
    private Set<ParameterDto> parameterDtoSet;

}
