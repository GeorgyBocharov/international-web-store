package ru.sbt.store.core.converters;

import org.springframework.stereotype.Service;
import ru.sbt.store.core.dto.ParameterTypeDto;
import ru.sbt.store.core.entities.ParameterType;

@Service
public interface ParameterTypeConversionService extends DtoConversionService<ParameterTypeDto, ParameterType, Long> {
}
