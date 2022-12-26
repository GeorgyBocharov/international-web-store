package ru.sbt.store.read.api.client;

import ru.sbt.common.entities.LanguageDto;
import ru.sbt.common.entities.ParameterTypeDto;
import ru.sbt.common.entities.ProductDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


import java.util.List;
import java.util.Optional;


@FeignClient(name = "core")
@Service
public interface CoreClient {

    @GetMapping("/products/{productId}/languages/{languageId}")
    Optional<ProductDto> getProductById(@PathVariable("productId") Long productId, @PathVariable("languageId") Long languageId);

    @GetMapping("/products/all")
    List<ProductDto> getAllProducts();

    @GetMapping(value = "/languages/{id}")
    Optional<LanguageDto> getLanguage(@PathVariable("id") Long id);

    @GetMapping(value = "/languages/all")
    List<LanguageDto> getAllLanguages();

    @GetMapping(value = "/parameter_types/{id}")
    Optional<ParameterTypeDto> getParameterType(@PathVariable("id") Long id);

    @GetMapping(value = "/parameter_types/all")
    List<ParameterTypeDto> getAllParameterTypes();
}
