package ru.sbt.store.read.api.configuration;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import ru.sbt.common.entities.*;
import ru.sbt.store.read.api.client.CoreClient;
import ru.sbt.store.read.api.converter.ProductConverter;
import ru.sbt.store.read.api.converter.impl.ProductConverterImpl;
import ru.sbt.store.read.api.exceptions.EntityNotFoundException;

import java.util.concurrent.TimeUnit;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class ProductCacheConfig {

    private final CoreClient coreClient;
    private final LoadingCache<Long, LanguageDto> languageLoadingCache;
    private final LoadingCache<Long, ParameterTypeDto> parameterTypeLoadingCache;

    private ProductConverter productConverter;


    @Bean
    @Scope("singleton")
    public LoadingCache<ProductKey, Product> productLoadingCache() {
        return CacheBuilder.newBuilder()
                .refreshAfterWrite(60, TimeUnit.MINUTES)
                .maximumSize(50)
                .build(productCacheLoader());
    }

    @Bean
    public CacheLoader<ProductKey, Product> productCacheLoader() {
        return new CacheLoader<ProductKey, Product>() {
            @Override
            public Product load(ProductKey key) {
                log.warn("Product with key {} wasn't cached yet, caching...", key);
                ProductDto productDto = coreClient.getProductById(key.getProductId(), key.getLanguageId())
                        .orElseThrow(() -> new EntityNotFoundException(Product.class.getName(), key.toString()));
                return productConverter.convertFromDtoWithSingleInfo(productDto);
            }
        };
    }

    @Bean
    @Scope("singleton")
    public ProductConverter productConverter() {
        return new ProductConverterImpl(languageLoadingCache, parameterTypeLoadingCache);
    }

    @Autowired
    public void setProductConverter(ProductConverter productConverter) {
        this.productConverter = productConverter;
    }
}
