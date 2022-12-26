package ru.sbt.store.core.services.impl;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.sbt.store.core.exceptions.FailedUpdateException;
import ru.sbt.store.core.repositories.ParameterTypeRepository;
import ru.sbt.store.core.services.ParameterTypeService;
import ru.sbt.store.core.entities.ParameterType;

import java.util.List;

@Component
@Slf4j
public class ParameterTypeServiceImpl implements ParameterTypeService {

    private ParameterTypeRepository repository;

    @Override
    public ParameterType saveNewObject(@NonNull String parameterTypeName) {
        ParameterType parameterType = new ParameterType();
        parameterType.setName(parameterTypeName);
        return repository.save(parameterType);
    }

    @Override
    public ParameterType saveNewObject(@NonNull ParameterType parameterType) {
        if (parameterType.getId() != null) {
            log.error("Try to save existing parameterType as new, use update method for such purposes");
            return parameterType;
        }
        return repository.save(parameterType);
    }

    @Override
    public ParameterType findObjectById(@NonNull Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public ParameterType updateObject(@NonNull ParameterType parameterType) {
        if (parameterType.getId() == null) {
            log.error("Attempt to update parameterType which isn't persisted");
            throw new FailedUpdateException(ParameterType.class.getName());
        }
        repository.updateParameterType(parameterType.getId(), parameterType.getName());
        return parameterType;
    }

    @Override
    public void deleteObjectById(@NonNull Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<ParameterType> findAllObjects() {
        return repository.findAll();
    }

    @Autowired
    public void setRepository(ParameterTypeRepository repository) {
        this.repository = repository;
    }
}
