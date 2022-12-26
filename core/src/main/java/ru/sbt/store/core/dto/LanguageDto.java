package ru.sbt.store.core.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Pattern;

@Getter
@Setter
public class LanguageDto {

    private Long id;

    @Length(min = 2, max = 50)
    @Pattern(regexp = "^[A-Za-z_ ]+$")
    private String name;

}
