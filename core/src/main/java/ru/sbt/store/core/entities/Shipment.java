package ru.sbt.store.core.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "shipments")
@Getter
@Setter
public class Shipment extends BaseEntity<Long>{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "shipments_id_gen")
    @SequenceGenerator(name = "shipments_id_gen", sequenceName = "shipments_id_seq", allocationSize = 1)
    private Long id;

    private String address;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;


    public Long getOrderId() {
        return order == null ? null : order.getId();
    }

    @Override
    public String toString() {
        return "Shipment{" +
                "id=" + id +
                ", orderId=" + getOrderId() +
                ", address='" + address + '\'' +
                '}';
    }
}
