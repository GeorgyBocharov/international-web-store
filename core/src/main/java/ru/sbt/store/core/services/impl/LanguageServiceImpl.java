package ru.sbt.store.core.services.impl;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.sbt.store.core.exceptions.FailedUpdateException;
import ru.sbt.store.core.repositories.LanguageRepository;
import ru.sbt.store.core.services.LanguageService;
import ru.sbt.store.core.entities.Language;

import java.util.List;

@Component
@Slf4j
public class LanguageServiceImpl implements LanguageService {

    private LanguageRepository repository;

    @Override
    public Language saveNewObject(@NonNull String languageName) {
        Language language = new Language();
        language.setName(languageName);
        return repository.save(language);
    }

    @Override
    public Language saveNewObject(@NonNull Language language) {
        if (language.getId() != null) {
            log.error("Try to save existing language as new, use update method for such purposes");
            return language;
        }
        return repository.save(language);
    }

    @Override
    public Language findObjectById(@NonNull Long id) {
        return repository.findById(id).orElse(null);
    }


    @Override
    public Language updateObject(@NonNull Language language) {
        if (language.getId() == null) {
            log.error("Attempt to update language which isn't persisted");
            throw new FailedUpdateException(Language.class.getName());
        }
        repository.updateLanguage(language.getId(), language.getName());
        return language;
    }

    @Override
    public void deleteObjectById(@NonNull Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<Language> findAllObjects() {
        return repository.findAll();
    }

    @Autowired
    public void setRepository(LanguageRepository repository) {
        this.repository = repository;
    }
}
