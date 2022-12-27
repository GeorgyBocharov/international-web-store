package ru.sbt.store.core.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.io.Serializable;
import java.time.ZonedDateTime;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity<T extends Serializable> {

    @CreationTimestamp
    @Column(name = "creation_date", columnDefinition="TIMESTAMP", updatable = false)
    private ZonedDateTime creationDate;

    @UpdateTimestamp
    @Column(name = "last_update_date", columnDefinition="TIMESTAMP")
    private ZonedDateTime lastUpdateDate;

    @Column
    @Version
    private Long version;

}
