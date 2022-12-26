package ru.sbt.store.core.dto;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;

@Getter
@Setter
public class InfoDto {

    private Long id;

    private Long productId;

    private Long languageId;

    @Valid
    private LanguageDto language;

    @Length(min = 3, max = 50)
    private String title;

    @Length(min = 3, max = 200)
    private String description;


}
