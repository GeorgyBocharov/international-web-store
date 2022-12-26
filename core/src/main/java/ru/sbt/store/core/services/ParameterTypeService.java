package ru.sbt.store.core.services;

import lombok.NonNull;
import org.springframework.stereotype.Service;
import ru.sbt.store.core.entities.ParameterType;

@Service
public interface ParameterTypeService extends CommonCRUDService<ParameterType, Long> {

    ParameterType saveNewObject(@NonNull String parameterTypeName);

}
