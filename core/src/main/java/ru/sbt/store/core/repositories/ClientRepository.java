package ru.sbt.store.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.sbt.store.core.entities.Client;


@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    @Transactional(readOnly = true)
    @Query("select COUNT(c.id) from Client c where c.phone = :phone")
    Integer findClientsNumberByPhone(@Param("phone") String phone);
}
