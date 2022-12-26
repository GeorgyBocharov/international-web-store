package ru.sbt.store.core.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "currencies")
@Getter
@Setter
public class Currency extends BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "currencies_id_gen")
    @SequenceGenerator(name = "currencies_id_gen", sequenceName = "currencies_id_seq", allocationSize = 1)
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
