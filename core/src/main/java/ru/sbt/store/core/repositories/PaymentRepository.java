package ru.sbt.store.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.sbt.store.core.entities.Payment;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    @Transactional(readOnly = true)
    @Query("select p from Payment p left join fetch p.currency c where p.id=:id")
    Optional<Payment> findPaymentById(@Param("id") Long id);
}
