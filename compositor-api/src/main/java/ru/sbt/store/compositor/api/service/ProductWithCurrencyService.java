package ru.sbt.store.compositor.api.service;

import reactor.core.publisher.Mono;
import ru.sbt.store.compositor.api.entities.ProductWithCurrency;

public interface ProductWithCurrencyService {
    Mono<ProductWithCurrency> constructProductWithCurrencyMono(Long productId, Long languageId);
}
