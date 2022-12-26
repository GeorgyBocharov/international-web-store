package ru.sbt.store.core.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "infos")
@Getter
@Setter
public class Info extends BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "infos_id_gen")
    @SequenceGenerator(name = "infos_id_gen", sequenceName = "infos_id_seq", allocationSize = 1)
    private Long id;

    private String title;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "language_id")
    private Language language;

    public Long getProductId() {
        return product == null ? null : product.getId();
    }

    public Long getLanguageId() {
        return language == null ? null : language.getId();
    }

    @Override
    public String toString() {
        return "Info{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", product=" + getProductId() +
                ", language=" + getLanguageId() +
                '}';
    }
}
