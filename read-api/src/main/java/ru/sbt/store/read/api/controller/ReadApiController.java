package ru.sbt.store.read.api.controller;

import com.google.common.cache.LoadingCache;
import ru.sbt.common.entities.Product;
import ru.sbt.common.entities.ProductKey;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import ru.sbt.store.read.api.exceptions.ReadApiException;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/read-api/products")
@RequiredArgsConstructor
public class ReadApiController {

    private final LoadingCache<ProductKey, Product> loadingCache;


    @GetMapping("/{productId}/language/{languageId}")
    public Mono<Product> getProductByIdAndLanguage(@PathVariable("productId") long productId, @PathVariable("languageId") long languageId)  {
        ProductKey productKey = new ProductKey(productId, languageId);
        Product product;
        try {
            product = loadingCache.get(productKey);
        } catch (ExecutionException ex) {
            throw new ReadApiException(ex);
        }
        return Mono.justOrEmpty(Optional.ofNullable(product));
    }

}
