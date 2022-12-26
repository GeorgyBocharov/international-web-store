package ru.sbt.store.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.sbt.store.core.entities.Order;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Transactional(readOnly = true)
    @Query("select o from Order o left join fetch o.items i left join fetch o.payment p  left join fetch o.currency c left join fetch o.shipment s left join fetch i.product prod where o.id= :id")
    Optional<Order> findOrderById(@Param("id") Long id);

    @Transactional(readOnly = true)
    @Query("select o from Order o where o.client.id=:id")
    List<Order> findOrdersByClientId(@Param("id") Long id);
}
