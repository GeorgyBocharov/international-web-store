package ru.sbt.store.core.converters;

import org.springframework.stereotype.Service;
import ru.sbt.store.core.dto.ProductDto;
import ru.sbt.store.core.entities.Product;

@Service
public interface ProductConversionService extends DtoConversionService<ProductDto, Product, Long> {
}
