package ru.sbt.store.core.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "clients", schema = "online_store")
@Getter
@Setter
public class Client extends BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "clients_id_seq")
    private Long id;

    private String name;

    private String phone;

    private Integer region;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Order> orders = new HashSet<>();

    public void addOrder(Order order) {
        orders.add(order);
    }

    public String getEncryptedPhone() {
        if (phone != null) {
            String encoding = "*****";
            return phone.substring(0, 5) + encoding + phone.substring(5 + encoding.length());
        }
        return null;
    }

    public void setPhone(String phone) {
        if (phone != null) {
            if (phone.charAt(0) == '8') {
                phone = "+7" + phone.substring(1);
            }
        }
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", region=" + region +
                '}';
    }
}
