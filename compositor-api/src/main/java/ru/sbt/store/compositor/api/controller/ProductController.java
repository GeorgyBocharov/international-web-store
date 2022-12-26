package ru.sbt.store.compositor.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import ru.sbt.store.compositor.api.entities.ProductWithCurrency;
import ru.sbt.store.compositor.api.service.ProductWithCurrencyService;



@RestController
@RequestMapping("/compositor-api")
@RequiredArgsConstructor
public class ProductController {

    private final ProductWithCurrencyService productWithCurrencyService;

    @GetMapping("/products/{productId}/language/{languageId}")
    public Mono<ProductWithCurrency> getProductWithCurrency(@PathVariable("productId") Long productId, @PathVariable("languageId") Long languageId) {
        return productWithCurrencyService.constructProductWithCurrencyMono(productId, languageId);
    }


}
