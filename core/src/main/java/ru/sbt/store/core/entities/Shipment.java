package ru.sbt.store.core.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "shipments", schema = "online_store")
@Getter
@Setter
public class Shipment extends BaseEntity<Long>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "shipments_id_seq")
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
