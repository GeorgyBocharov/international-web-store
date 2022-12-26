package ru.sbt.store.core.converters.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.sbt.store.core.converters.ParameterConversionService;
import ru.sbt.store.core.converters.ParameterTypeConversionService;
import ru.sbt.store.core.dto.ParameterDto;
import ru.sbt.store.core.entities.Parameter;
import ru.sbt.store.core.entities.ParameterType;
import ru.sbt.store.core.entities.Product;

@Component
@RequiredArgsConstructor
public class ParameterConversionServiceImpl implements ParameterConversionService {

    private final ParameterTypeConversionService parameterTypeConversionService;

    @Override
    public ParameterDto convertToDto(Parameter parameter) {
        if (parameter == null) {
            return null;
        }
        ParameterDto parameterDto = convertToDtoNotFetched(parameter);
        parameterDto.setParameterTypeDto(parameterTypeConversionService.convertToDto(parameter.getParameterType()));
        return parameterDto;
    }

    @Override
    public ParameterDto convertToDtoNotFetched(Parameter parameter) {
        if (parameter == null) {
            return null;
        }
        ParameterDto parameterDto = new ParameterDto();
        parameterDto.setId(parameter.getId());
        parameterDto.setValue(parameter.getValue());
        parameterDto.setProductId(parameter.getProductId());
        parameterDto.setParameterTypeId(parameter.getParameterTypeId());
        return parameterDto;
    }

    @Override
    public Parameter convertFromDto(ParameterDto parameterDto) {
        if (parameterDto == null) {
            return null;
        }
        Parameter parameter = new Parameter();
        parameter.setId(parameterDto.getId());
        parameter.setValue(parameterDto.getValue());

        ParameterType parameterType = new ParameterType();
        parameterType.setId(parameterDto.getParameterTypeId());
        parameter.setParameterType(parameterType);

        if (parameterDto.getProductId() != null) {
            Product product = new Product();
            product.setId(parameterDto.getProductId());
            parameter.setProduct(product);
        }


        return parameter;
    }
}
