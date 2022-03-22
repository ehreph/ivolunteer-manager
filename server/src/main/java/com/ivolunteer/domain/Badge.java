package com.ivolunteer.domain;


import javax.persistence.*;

import java.io.Serializable;

/**
 * A Badge.
 */
@Entity
@DiscriminatorValue("badge")
public class Badge extends BaseData implements Serializable {

    private static final long serialVersionUID = 1L;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Badge name(String name) {
        this.setName(name);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here


    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Badge{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
