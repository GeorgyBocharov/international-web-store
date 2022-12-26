package ru.sbt.store.core.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.sbt.store.core.converters.LanguageConversionService;
import ru.sbt.store.core.dto.LanguageDto;
import ru.sbt.store.core.entities.Language;
import ru.sbt.store.core.services.LanguageService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/languages")
@RestController
@RequiredArgsConstructor
@Validated
@Slf4j
public class LanguageController {
    
    private final LanguageService languageService;
    private final LanguageConversionService languageConversionService;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public LanguageDto getLanguage(@PathVariable("id") Long id) {
        Language language = languageService.findObjectById(id);
        return languageConversionService.convertToDto(language);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public LanguageDto createLanguage(@Valid @RequestBody LanguageDto languageDto) {
        Language language = languageConversionService.convertFromDto(languageDto);
        Language savedLanguage = languageService.saveNewObject(language);
        return languageConversionService.convertToDto(savedLanguage);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public LanguageDto updateLanguage(@Valid @RequestBody LanguageDto languageDto) {
        Language language = languageConversionService.convertFromDto(languageDto);
        Language updatedLanguage = languageService.updateObject(language);
        return languageConversionService.convertToDto(updatedLanguage);
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<LanguageDto> getAllLanguages() {
        log.info("Getting all languages...");
        return languageService.findAllObjects()
                .stream()
                .map(languageConversionService::convertToDto)
                .collect(Collectors.toList());
    }

    @DeleteMapping(value = "/{id}")
    public void deleteLanguage(@PathVariable("id") Long id) {
        languageService.deleteObjectById(id);
    }
}
