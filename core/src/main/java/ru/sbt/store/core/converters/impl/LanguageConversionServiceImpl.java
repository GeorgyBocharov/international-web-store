package ru.sbt.store.core.converters.impl;

import org.springframework.stereotype.Component;
import ru.sbt.store.core.converters.LanguageConversionService;
import ru.sbt.store.core.dto.LanguageDto;
import ru.sbt.store.core.entities.Language;

@Component
public class LanguageConversionServiceImpl implements LanguageConversionService {

    @Override
    public LanguageDto convertToDto(Language language) {
        if (language == null) {
            return null;
        }
        LanguageDto languageDto = new LanguageDto();
        languageDto.setId(language.getId());
        languageDto.setName(language.getName());
        return languageDto;
    }

    @Override
    public Language convertFromDto(LanguageDto languageDto) {
        if (languageDto == null) {
            return null;
        }
        Language language = new Language();
        language.setName(languageDto.getName());
        language.setId(languageDto.getId());
        return language;
    }
}
