package ru.sbt.store.read.api.converter;


import ru.sbt.common.entities.Product;
import ru.sbt.common.entities.ProductDto;
import ru.sbt.common.entities.ProductKey;

import java.util.Collection;
import java.util.Map;

public interface ProductConverter {

    Map<ProductKey, Product> convertFromDtosWithMultipleInfos(Collection<ProductDto> productDtos);
    Product convertFromDtoWithSingleInfo(ProductDto productDto);
}
