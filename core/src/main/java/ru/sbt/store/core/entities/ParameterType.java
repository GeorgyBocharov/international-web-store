package ru.sbt.store.core.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "parameter_types", schema = "online_store")
@Getter
@Setter
public class ParameterType extends BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "parameter_types_id_seq")
    private Long id;

    @Column
    private String name;

    @Override
    public String toString() {
        return "ParameterType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
