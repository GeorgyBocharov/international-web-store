package ru.sbt.store.core.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;


@Getter
@Setter
public class ParameterTypeDto {

    private Long id;

    @Length(min = 2, max = 50)
    private String name;

}
