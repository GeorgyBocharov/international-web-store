package ru.sbt.store.core.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "currencies", schema = "online_store")
@Getter
@Setter
public class Currency extends BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "currencies_id_seq")
    private Long id;

    private String name;

    private BigDecimal multiplier = BigDecimal.valueOf(1.);

    @Override
    public String toString() {
        return "Currency{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", multiplier=" + multiplier +
                '}';
    }
}
