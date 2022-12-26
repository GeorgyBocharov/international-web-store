package ru.sbt.store.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.sbt.store.core.entities.Currency;

import java.math.BigDecimal;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Long> {

    @Transactional(readOnly = true)
    @Query("select c from Currency c where c.name = :name")
    Currency findByName(@Param("name") String name);

    @Query("delete from Currency c where c.name = :name")
    @Transactional
    void deleteByName(@Param("name") String name);

    @Transactional
    @Modifying
    @Query("update Currency c set c.name = :name, c.multiplier = :multiplier where c.id =:id")
    void updateCurrency(@Param("id") Long id, @Param("name") String name, @Param("multiplier") BigDecimal multiplier);

}
