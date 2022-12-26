package ru.sbt.store.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.sbt.store.core.entities.Info;

import java.util.Collection;
import java.util.List;

@Repository
public interface InfoRepository extends JpaRepository<Info, Long> {

    @Query("select i from Info i where i.product.id in :ids")
    List<Info> findAllByProductIdIn(@Param("ids") Collection<Long> ids);
}
