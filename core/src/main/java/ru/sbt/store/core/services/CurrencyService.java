package ru.sbt.store.core.services;

import lombok.NonNull;
import org.springframework.stereotype.Service;
import ru.sbt.store.core.entities.Currency;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public interface CurrencyService extends CommonCRUDService<Currency, Long> {

    Currency saveNewObject(@NonNull String currencyName, BigDecimal multiplier);
    void deleteObjectByName(@NonNull String currencyName);
    Optional<Currency> findObjectByName(@NonNull String currencyName);
}
