package com.ivolunteer.domain;


import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cache;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "base_data")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "base_type",
    discriminatorType = DiscriminatorType.STRING)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BaseData implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(name = "name")
    protected String name;

    public BaseData name(String name) {
        this.name = name;
        return this;
    }


    public BaseData id(Long id) {
        this.id = id;
        return this;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BaseData)) {
            return false;
        }
        return id != null && id.equals(((BaseData) o).id);
    }

    @Override
    public String toString() {
        return "BaseData{" +
            "id=" + id +
            ", name='" + name + '\'' +
            '}';
    }
}
