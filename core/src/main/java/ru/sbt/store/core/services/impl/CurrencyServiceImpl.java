package ru.sbt.store.core.services.impl;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.sbt.store.core.exceptions.FailedUpdateException;
import ru.sbt.store.core.repositories.CurrencyRepository;
import ru.sbt.store.core.services.CurrencyService;
import ru.sbt.store.core.entities.Currency;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class CurrencyServiceImpl implements CurrencyService {

    private CurrencyRepository repository;

    @Override
    public Currency saveNewObject(@NonNull String currencyName, BigDecimal multiplier) {
        Currency currency = new Currency();
        currency.setName(currencyName);
        currency.setMultiplier(multiplier);
        return repository.save(currency);
    }

    @Override
    public void deleteObjectByName(@NonNull String currencyName) {
        repository.deleteByName(currencyName);
    }

    @Override
    public Optional<Currency> findObjectByName(@NonNull String currencyName) {
        return repository.findByName(currencyName);
    }

    @Override
    public Currency saveNewObject(@NonNull Currency currency) {
        if (currency.getId() != null) {
            log.error("Try to save existing currency as new, use update method for such purposes");
            return currency;
        }
        return repository.save(currency);
    }

    @Override
    public Currency findObjectById(@NonNull Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Currency updateObject(@NonNull Currency currency) {
        if (currency.getId() == null) {
            log.error("Attempt to update currency which isn't persisted");
            throw new FailedUpdateException(Currency.class.getName());
        }
        repository.updateCurrency(currency.getId(), currency.getName(), currency.getMultiplier());
        return currency;
    }

    @Override
    public void deleteObjectById(@NonNull Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<Currency> findAllObjects() {
        return repository.findAll();
    }

    @Autowired
    public void setRepository(CurrencyRepository repository) {
        this.repository = repository;
    }
}
