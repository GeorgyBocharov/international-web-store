package ru.sbt.store.core.wrapping.services.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.sbt.store.core.converters.InfoConversionService;
import ru.sbt.store.core.converters.ParameterConversionService;
import ru.sbt.store.core.converters.ProductConversionService;
import ru.sbt.store.core.dto.InfoDto;
import ru.sbt.store.core.dto.ParameterDto;
import ru.sbt.store.core.dto.ProductDto;
import ru.sbt.store.core.entities.Info;
import ru.sbt.store.core.entities.Parameter;
import ru.sbt.store.core.entities.Product;
import ru.sbt.store.core.wrapping.services.ProductDtoService;



import ru.sbt.store.core.services.ProductService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductDtoServiceImpl implements ProductDtoService {

    private final ProductService productService;
    private final ParameterConversionService parameterConversionService;
    private final InfoConversionService infoConversionService;
    private final ProductConversionService productConversionService;


    @Override
    public ProductDto saveNewObject(@NonNull ProductDto productDto) {
        Product product = productConversionService.convertFromDto(productDto);
        Product savedProduct = productService.saveNewObject(product);
        return productConversionService.convertToDto(savedProduct);
    }

    @Override
    public ProductDto findObjectByIdAndLanguage(@NonNull Long productId, @NonNull Long languageId) {
        Product product = productService.findProductByIdAndLanguage(productId, languageId);
        return convertProductDtoWithInfosAndParams(product);
    }

    @Override
    public ProductDto findObjectById(@NonNull Long id) {
        Product product = productService.findObjectById(id);
        return convertProductDtoWithInfosAndParams(product);
    }

    @Override
    public ProductDto updateObject(@NonNull ProductDto productDto) {
        Product product = productConversionService.convertFromDto(productDto);
        Product updatedProduct = productService.updateObject(product);
        return productConversionService.convertToDtoNotFetched(updatedProduct);
    }

    @Override
    public void deleteObjectById(@NonNull Long id) {
        productService.deleteObjectById(id);
    }

    @Override
    public List<ProductDto> findAllObjects() {
        List<Product> products = productService.findAllObjects();
        List<ProductDto> productDtoList = new ArrayList<>();
        for (Product product : products) {
            ProductDto productDto = convertProductDtoWithInfosAndParams(product);
            productDtoList.add(productDto);
        }
        return productDtoList;
    }


    @Override
    public InfoDto addInfo(InfoDto infoDto) {
        Info info = infoConversionService.convertFromDto(infoDto);
        Info savedInfo = productService.addInfo(info);
        return infoConversionService.convertToDto(savedInfo);
    }

    @Override
    public InfoDto updateInfo(InfoDto infoDto) {
        Info info = infoConversionService.convertFromDto(infoDto);
        Info updatedInfo = productService.updateInfo(info);
        return infoConversionService.convertToDtoNotFetched(updatedInfo);
    }

    @Override
    public void deleteInfo(Long infoId) {
        productService.deleteInfo(infoId);
    }

    @Override
    public ParameterDto addParameter(ParameterDto parameterDto) {
        Parameter parameter = parameterConversionService.convertFromDto(parameterDto);
        Parameter savedParameter = productService.addParameter(parameter);
        return parameterConversionService.convertToDto(savedParameter);
    }

    @Override
    public ParameterDto updateParameter(ParameterDto parameterDto) {
        Parameter parameter = parameterConversionService.convertFromDto(parameterDto);
        Parameter updatedParameter = productService.updateParameter(parameter);
        return parameterConversionService.convertToDtoNotFetched(updatedParameter);
    }

    @Override
    public void deleteParameter(Long parameterId) {
        productService.deleteParameter(parameterId);
    }


    private ProductDto convertProductDtoWithInfosAndParams(Product product) {
        if (product == null) {
            return null;
        }
        Set<InfoDto> infoDtoSet = product.getInfos()
                .stream()
                .map(infoConversionService::convertToDtoNotFetched)
                .collect(Collectors.toSet());

        Set<ParameterDto> parameterDtoSet = product.getParameters()
                .stream()
                .map(parameterConversionService::convertToDtoNotFetched)
                .collect(Collectors.toSet());

        ProductDto productDto = productConversionService.convertToDtoNotFetched(product);
        productDto.setInfoDtoSet(infoDtoSet);
        productDto.setParameterDtoSet(parameterDtoSet);
        return productDto;
    }
}
