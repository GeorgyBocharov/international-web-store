package ru.sbt.store.core.services;

import lombok.NonNull;
import org.springframework.stereotype.Service;
import ru.sbt.store.core.entities.Language;


@Service
public interface LanguageService extends CommonCRUDService<Language, Long> {
    Language saveNewObject(@NonNull String languageName);
}
