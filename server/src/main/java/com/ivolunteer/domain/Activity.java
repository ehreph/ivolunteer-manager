package com.ivolunteer.domain;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * A Activity.
 */
@Entity
@DiscriminatorValue("activity")
public class Activity extends BaseData implements Serializable {

    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "attributes_id")
    @JsonIgnoreProperties(value = "activity", allowSetters = true)
    private RuleAttribute attributes;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Activity name(String name) {
        this.setName(name);
        return this;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here


    public RuleAttribute getAttributes() {
        return attributes;
    }

    public void setAttributes(RuleAttribute attributes) {
        this.attributes = attributes;
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore


    @Override
    public String toString() {
        return "Activity{" +
            "attributes=" + attributes +
            "} " + super.toString();
    }
}
