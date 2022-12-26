package ru.sbt.store.core.converters;

import org.springframework.stereotype.Service;
import ru.sbt.store.core.dto.ParameterDto;
import ru.sbt.store.core.entities.Parameter;

@Service
public interface ParameterConversionService  extends DtoConversionService<ParameterDto, Parameter, Long> {

}
