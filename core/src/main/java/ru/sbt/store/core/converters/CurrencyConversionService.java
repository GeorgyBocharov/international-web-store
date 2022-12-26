package ru.sbt.store.core.converters;

import org.springframework.stereotype.Service;
import ru.sbt.store.core.dto.CurrencyDto;
import ru.sbt.store.core.entities.Currency;

@Service
public interface CurrencyConversionService extends DtoConversionService<CurrencyDto, Currency, Long> {

}
