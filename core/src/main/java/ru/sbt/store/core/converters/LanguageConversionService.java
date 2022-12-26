package ru.sbt.store.core.converters;

import org.springframework.stereotype.Service;
import ru.sbt.store.core.dto.LanguageDto;
import ru.sbt.store.core.entities.Language;

@Service
public interface LanguageConversionService extends DtoConversionService<LanguageDto, Language, Long> {
}
