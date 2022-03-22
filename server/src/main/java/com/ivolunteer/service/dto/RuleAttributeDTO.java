package com.ivolunteer.service.dto;

import java.io.Serializable;
import com.ivolunteer.domain.enumeration.GlobalType;
/**
 * A DTO for the {@link com.ivolunteer.domain.RuleAttribute} entity.
 */
public class RuleAttributeDTO implements Serializable {

    private Long id;

    private GlobalType ruleType;

    private String name;

    private String unitName;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GlobalType getRuleType() {
        return ruleType;
    }

    public void setRuleType(GlobalType ruleType) {
        this.ruleType = ruleType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RuleAttributeDTO)) {
            return false;
        }

        return id != null && id.equals(((RuleAttributeDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RuleAttributeDTO{" +
            "id=" + getId() +
            ", ruleType='" + getRuleType() + "'" +
            ", name='" + getName() + "'" +
            ", unitName='" + getUnitName() + "'" +
            "}";
    }
}
