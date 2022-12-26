package ru.sbt.store.core.converters.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.sbt.store.core.converters.InfoConversionService;
import ru.sbt.store.core.converters.ParameterConversionService;
import ru.sbt.store.core.converters.ProductConversionService;
import ru.sbt.store.core.dto.ProductDto;
import ru.sbt.store.core.entities.Product;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductConversionServiceImpl implements ProductConversionService {

    private final ParameterConversionService parameterConversionService;
    private final InfoConversionService infoConversionService;

    @Override
    public ProductDto convertToDto(Product product) {
        if (product == null) {
            return null;
        }
        ProductDto productDto = convertToDtoNotFetched(product);
        productDto.setParameterDtoSet(product.getParameters().stream().map(parameterConversionService::convertToDto).collect(Collectors.toSet()));
        productDto.setInfoDtoSet(product.getInfos().stream().map(infoConversionService::convertToDto).collect(Collectors.toSet()));
        return productDto;
    }

    @Override
    public ProductDto convertToDtoNotFetched(Product product) {
        if (product == null) {
            return null;
        }
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setPriceCU(product.getPriceCU());
        return productDto;
    }

    @Override
    public Product convertFromDto(ProductDto productDto) {
        if (productDto == null) {
            return null;
        }
        Product product = new Product();
        product.setId(productDto.getId());
        product.setPriceCU(productDto.getPriceCU());
        product.setParameters(
                productDto
                        .getParameterDtoSet()
                        .stream()
                        .map(parameterConversionService::convertFromDto)
                        .collect(Collectors.toSet())
        );
        product.setInfos(
                productDto
                        .getInfoDtoSet()
                        .stream()
                        .map(infoConversionService::convertFromDto)
                        .collect(Collectors.toSet())
        );

        log.debug("constructed product {} with infos {} and params {}", product, product.getInfos(), product.getParameters());

        return product;
    }
}
