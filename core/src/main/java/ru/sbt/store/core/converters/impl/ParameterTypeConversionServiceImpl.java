package ru.sbt.store.core.converters.impl;

import org.springframework.stereotype.Component;
import ru.sbt.store.core.converters.ParameterTypeConversionService;
import ru.sbt.store.core.dto.ParameterTypeDto;
import ru.sbt.store.core.entities.ParameterType;

@Component
public class ParameterTypeConversionServiceImpl implements ParameterTypeConversionService {

    @Override
    public ParameterTypeDto convertToDto(ParameterType parameterType) {
        if (parameterType == null) {
            return null;
        }
        ParameterTypeDto parameterTypeDto = new ParameterTypeDto();
        parameterTypeDto.setId(parameterType.getId());
        parameterTypeDto.setName(parameterType.getName());
        return parameterTypeDto;
    }

    @Override
    public ParameterType convertFromDto(ParameterTypeDto parameterTypeDto) {
        if (parameterTypeDto == null) {
            return null;
        }
        ParameterType parameterType = new ParameterType();
        parameterType.setName(parameterTypeDto.getName());
        parameterType.setId(parameterTypeDto.getId());
        return parameterType;
    }
}
