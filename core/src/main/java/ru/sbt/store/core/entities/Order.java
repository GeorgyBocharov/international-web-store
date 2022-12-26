package ru.sbt.store.core.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order extends BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orders_id_gen")
    @SequenceGenerator(name = "orders_id_gen", sequenceName = "orders_id_seq", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "currency_id")
    private Currency currency;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "client_id")
    private Client client;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<OrderItem> items = new HashSet<>();

    @OneToOne(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Shipment shipment;

    @OneToOne(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Payment payment;


    public void addItems(Collection<OrderItem> items) {
        this.items.addAll(items);
    }

    public void addItem(OrderItem item){
        items.add(item);
    }

    public Long getCurrencyId() {
        return currency == null ? null : currency.getId();
    }

    public Long getClientId() {
        return client == null ? null : client.getId();
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", currency=" + getCurrencyId() +
                ", client=" + getClientId() +
                '}';
    }
}
