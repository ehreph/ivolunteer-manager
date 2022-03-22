package com.ivolunteer.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;

import java.io.Serializable;

/**
 * A Competence.
 */
@Entity
@DiscriminatorValue("competence")
public class Competence extends BaseData implements Serializable{

    private static final long serialVersionUID = 1L;

    @Min(0)
    @Column(name = "max_level")
    private Double maxLevel;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Competence name(String name) {
        this.setName(name);
        return this;
    }

    public Double getMaxLevel() {
        return maxLevel;
    }

    public Competence maxLevel(Double maxLevel) {
        this.maxLevel = maxLevel;
        return this;
    }

    public void setMaxLevel(Double maxLevel) {
        this.maxLevel = maxLevel;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Competence)) {
            return false;
        }
        return id != null && id.equals(((Competence) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore


    @Override
    public String toString() {
        return "Competence{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", maxLevel=" + maxLevel +
            '}';
    }
}
