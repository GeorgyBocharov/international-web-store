package ru.sbt.store.core.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "parameter_types")
@Getter
@Setter
public class ParameterType extends BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "parameter_types_id_gen")
    @SequenceGenerator(name = "parameter_types_id_gen", sequenceName = "parameter_types_id_seq", allocationSize = 1)
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
