package ru.sbt.store.compositor.api.service.impl;

import ru.sbt.common.entities.CurrencyDto;
import ru.sbt.common.entities.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import ru.sbt.store.compositor.api.client.ReadApiClient;
import ru.sbt.store.compositor.api.entities.PriceInCurrency;
import ru.sbt.store.compositor.api.entities.ProductWithCurrency;
import ru.sbt.store.compositor.api.client.CoreClient;
import ru.sbt.store.compositor.api.service.ProductWithCurrencyService;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductWithCurrencyServiceImpl implements ProductWithCurrencyService {

    private final CoreClient coreClient;
    private final ReadApiClient readApiClient;

    public Mono<ProductWithCurrency> constructProductWithCurrencyMono(Long productId, Long languageId) {
        Mono<Product> productMono = Mono.fromCallable(() -> readApiClient.getProductByIdAndLanguage(productId, languageId));
        Mono<List<CurrencyDto>> currenciesMono = Mono.fromCallable(coreClient::getAllCurrencies);
        return productMono.zipWith(currenciesMono).map(ProductWithCurrencyServiceImpl::constructProductWithCurrency);
    }

    private static ProductWithCurrency constructProductWithCurrency(Tuple2<Product, List<CurrencyDto>> tuple) {
        Product product = tuple.getT1();
        BigDecimal priceCU = product.getPriceCU();
        ProductWithCurrency.ProductWithCurrencyBuilder productWithCurrency = ProductWithCurrency.builder()
                .id(product.getId())
                .info(product.getInfo())
                .priceCU(product.getPriceCU())
                .parameters(product.getParameters());
        for (CurrencyDto currencyDto : tuple.getT2()) {
            BigDecimal priceInCurrency = currencyDto.getMultiplier().multiply(priceCU);
            productWithCurrency.priceInCurrency(new PriceInCurrency(currencyDto, priceInCurrency));
        }
        return productWithCurrency.build();
    }
}
