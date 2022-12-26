package ru.sbt.store.core.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.sbt.store.core.converters.CurrencyConversionService;
import ru.sbt.store.core.dto.CurrencyDto;
import ru.sbt.store.core.entities.Currency;
import ru.sbt.store.core.services.CurrencyService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/currencies")
@RestController
@RequiredArgsConstructor
@Validated
public class CurrencyController {
    
    private final CurrencyService currencyService;
    private final CurrencyConversionService currencyConversionService;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CurrencyDto getCurrency(@PathVariable("id") Long id) {
        Currency currency = currencyService.findObjectById(id);
        return currencyConversionService.convertToDto(currency);
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CurrencyDto> getAllCurrencies() {
        return currencyService.findAllObjects().stream().map(currencyConversionService::convertToDto).collect(Collectors.toList());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public CurrencyDto createCurrency(@Valid @RequestBody CurrencyDto currencyDto) {
        Currency currency = currencyConversionService.convertFromDto(currencyDto);
        Currency savedCurrency = currencyService.saveNewObject(currency);
        return currencyConversionService.convertToDto(savedCurrency);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public CurrencyDto updateCurrency(@Valid @RequestBody CurrencyDto currencyDto) {
        Currency currency = currencyConversionService.convertFromDto(currencyDto);
        Currency updatedCurrency = currencyService.updateObject(currency);
        return currencyConversionService.convertToDto(updatedCurrency);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteCurrency(@PathVariable("id") Long id) {
        currencyService.deleteObjectById(id);
    }

}
