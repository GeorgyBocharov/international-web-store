package ru.sbt.store.read.api.converter.impl;

import com.google.common.cache.LoadingCache;
import common.entities.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.sbt.common.entities.*;
import ru.sbt.store.read.api.converter.ProductConverter;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Slf4j
@AllArgsConstructor
public class ProductConverterImpl implements ProductConverter {

    private final LoadingCache<Long, LanguageDto> languageLoadingCache;
    private final LoadingCache<Long, ParameterTypeDto> parameterTypeLoadingCache;

    @Override
    public Product convertFromDtoWithSingleInfo(ProductDto dto) {
        if (dto == null) {
            return null;
        }
        uploadCachedValues(dto);
        Product product = new Product();
        product.setId(dto.getId());
        product.setPriceCU(dto.getPriceCU());
        product.setParameters(dto.getParameterDtoSet());
        if (dto.getInfoDtoSet().size() > 1) {
            log.error("Expected only one info in productDto");
        } else if (dto.getInfoDtoSet().isEmpty()) {
            log.warn("Gor product Dto without infos");
            return product;
        }
        product.setInfo(dto.getInfoDtoSet().iterator().next());

        return product;
    }

    @Override
    public Map<ProductKey, Product> convertFromDtosWithMultipleInfos(Collection<ProductDto> productDtos) {
        Map<ProductKey, Product> productMap = new HashMap<>();

        for (ProductDto productDto : productDtos) {
            uploadCachedValues(productDto);
            for (InfoDto infoDto : productDto.getInfoDtoSet()) {
                Product product = new Product();
                product.setId(productDto.getId());
                product.setPriceCU(productDto.getPriceCU());
                product.setParameters(productDto.getParameterDtoSet());
                product.setInfo(infoDto);
                ProductKey productKey = new ProductKey(product.getId(), infoDto.getLanguageId());
                productMap.put(productKey, product);
            }
        }

        log.trace("Exiting with productMap {}", productMap);

        return productMap;
    }

    private void uploadCachedValues(ProductDto productDto) {
            for (ParameterDto parameterDto: productDto.getParameterDtoSet()) {
                if (parameterDto.getParameterTypeDto() != null) {
                    continue;
                }
                try {
                    parameterDto.setParameterTypeDto(parameterTypeLoadingCache.get(parameterDto.getParameterTypeId()));
                } catch (ExecutionException ex) {
                    log.error("EXECUTION EXCEPTION: ", ex);
                }
            }

            for (InfoDto infoDto: productDto.getInfoDtoSet()) {
                if (infoDto.getLanguage() != null) {
                    continue;
                }
                try {
                    infoDto.setLanguage(languageLoadingCache.get(infoDto.getLanguageId()));
                } catch (ExecutionException ex) {
                    log.error("EXECUTION EXCEPTION: ", ex);
                }
            }
    }
}
