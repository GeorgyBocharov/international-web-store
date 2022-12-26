package ru.sbt.store.core.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "languages")
@Getter
@Setter
public class Language extends BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "languages_id_gen")
    @SequenceGenerator(name = "languages_id_gen", sequenceName = "languages_id_seq", allocationSize = 1)
    private Long id;

    private String name;

    @Override
    public String toString() {
        return "Language{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
