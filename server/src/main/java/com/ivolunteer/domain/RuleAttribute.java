package com.ivolunteer.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

import com.ivolunteer.domain.enumeration.GlobalType;

/**
 * A RuleAttribute.
 */
@Entity
@Table(name = "rule_attribute")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RuleAttribute implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "rule_type")
    private GlobalType ruleType;

    @Column(name = "name")
    private String name;

    @Column(name = "unit_name")
    private String unitName;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GlobalType getRuleType() {
        return ruleType;
    }

    public RuleAttribute ruleType(GlobalType ruleType) {
        this.ruleType = ruleType;
        return this;
    }

    public void setRuleType(GlobalType ruleType) {
        this.ruleType = ruleType;
    }

    public String getName() {
        return name;
    }

    public RuleAttribute name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getUnitName() {
        return unitName;
    }

    public RuleAttribute unitName(String unitName) {
        this.unitName = unitName;
        return this;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RuleAttribute)) {
            return false;
        }
        return id != null && id.equals(((RuleAttribute) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RuleAttribute{" +
            "id=" + getId() +
            ", ruleType='" + getRuleType() + "'" +
            ", name='" + getName() + "'" +
            ", unitName='" + getUnitName() + "'" +
            "}";
    }
}
