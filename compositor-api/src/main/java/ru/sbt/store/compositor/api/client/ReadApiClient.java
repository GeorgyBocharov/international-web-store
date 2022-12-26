package ru.sbt.store.compositor.api.client;

import ru.sbt.common.entities.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "read-api")
public interface ReadApiClient {
    @GetMapping("/read-api/products/{productId}/language/{languageId}")
    Product getProductByIdAndLanguage(@PathVariable("productId") Long productId, @PathVariable("languageId") Long languageId);
}
