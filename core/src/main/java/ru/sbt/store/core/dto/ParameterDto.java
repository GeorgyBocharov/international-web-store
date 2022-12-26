package ru.sbt.store.core.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;

@Getter
@Setter
public class ParameterDto {

    private Long id;

    private Long parameterTypeId;

    private Long productId;

    @Valid
    private ParameterTypeDto parameterTypeDto;

    @Length(min = 3, max = 100)
    private String value;


}
