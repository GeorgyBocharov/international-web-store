package ru.sbt.store.core.converters;

import ru.sbt.store.core.entities.BaseEntity;

import java.io.Serializable;

public interface DtoConversionService<D, T extends BaseEntity<ID>, ID extends Serializable> {

    D convertToDto(T entity);
    T convertFromDto(D dto);

    default D convertToDtoNotFetched(T entity) {
        return null;
    }
}
