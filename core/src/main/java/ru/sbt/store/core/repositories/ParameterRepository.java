package ru.sbt.store.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.sbt.store.core.entities.Parameter;

import java.util.Collection;
import java.util.List;

@Repository
public interface ParameterRepository extends JpaRepository<Parameter, Long> {

    @Query("select p from Parameter p where p.product.id in :ids")
    List<Parameter> findAllByProductIdIn(@Param("ids") Collection<Long> ids);
}
