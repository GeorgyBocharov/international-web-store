package ru.sbt.store.core.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "products", schema = "online_store")
@Getter
@Setter
public class Product extends BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "products_id_seq")
    private Long id;

    @Column(name = "price_cu")
    private BigDecimal priceCU;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Parameter> parameters = new HashSet<>();

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Info> infos = new HashSet<>();

    public void addParameter(Parameter parameter) {
        this.parameters.add(parameter);
    }

    public void addInfo(Info info) {
        this.infos.add(info);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", priceCU=" + priceCU +
                '}';
    }
}
