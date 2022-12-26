package ru.sbt.store.core.services;


import lombok.NonNull;
import ru.sbt.store.core.entities.BaseEntity;

import java.io.Serializable;
import java.util.List;

public interface CommonCRUDService<T extends BaseEntity<ID>, ID extends Serializable> {

    T saveNewObject(@NonNull T object);
    T findObjectById(@NonNull ID id);
    T updateObject(@NonNull T object);
    void deleteObjectById(@NonNull ID id);
    List<T> findAllObjects();

}
