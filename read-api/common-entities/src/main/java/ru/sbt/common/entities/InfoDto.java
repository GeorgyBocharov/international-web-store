package ru.sbt.common.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class InfoDto {

    private Long id;

    private Long productId;

    private Long languageId;

    private LanguageDto language;

    private String title;

    private String description;


}
