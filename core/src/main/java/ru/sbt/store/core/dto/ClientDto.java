package ru.sbt.store.core.dto;

import lombok.*;
import ru.sbt.store.core.validation.ValidPhoneNumber;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class ClientDto {

    private Long id;

    @NotBlank
    private String name;

    @Min(1)
    @Max(10000)
    private Integer region;

    @ValidPhoneNumber
    private String phone;

}
