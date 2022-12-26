package ru.sbt.store.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.sbt.store.core.entities.ParameterType;

@Repository
public interface ParameterTypeRepository extends JpaRepository<ParameterType, Long> {

    @Transactional
    @Modifying
    @Query("update ParameterType p set p.name = :name where p.id = :id")
    void updateParameterType(@Param("id") Long id, @Param("name") String name);
}
