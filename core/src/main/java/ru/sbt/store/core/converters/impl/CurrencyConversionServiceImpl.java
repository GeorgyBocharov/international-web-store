package ru.sbt.store.core.converters.impl;

import org.springframework.stereotype.Component;
import ru.sbt.store.core.converters.CurrencyConversionService;
import ru.sbt.store.core.dto.CurrencyDto;
import ru.sbt.store.core.entities.Currency;

@Component
public class CurrencyConversionServiceImpl implements CurrencyConversionService {

    @Override
    public CurrencyDto convertToDto(Currency currency) {
        if (currency == null) {
            return null;
        }
        CurrencyDto currencyDto = new CurrencyDto();
        currencyDto.setId(currency.getId());
        currencyDto.setMultiplier(currency.getMultiplier());
        currencyDto.setName(currency.getName());
        return currencyDto;
    }

    @Override
    public Currency convertFromDto(CurrencyDto currencyDto) {
        if (currencyDto == null) {
            return null;
        }
        Currency currency = new Currency();
        currency.setMultiplier(currencyDto.getMultiplier());
        currency.setName(currencyDto.getName());
        currency.setId(currencyDto.getId());
        return currency;
    }
}
