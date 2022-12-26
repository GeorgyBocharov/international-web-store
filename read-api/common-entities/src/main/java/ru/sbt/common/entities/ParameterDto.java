package ru.sbt.common.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ParameterDto {

    private Long id;

    private Long parameterTypeId;

    private Long productId;

    private ParameterTypeDto parameterTypeDto;

    private String value;


}
