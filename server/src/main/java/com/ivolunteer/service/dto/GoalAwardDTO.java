package com.ivolunteer.service.dto;

import java.io.Serializable;
import com.ivolunteer.domain.enumeration.GlobalType;

/**
 * A DTO for the {@link com.ivolunteer.domain.GoalAward} entity.
 */
public class GoalAwardDTO implements Serializable {

    private Long id;

    private GlobalType awardType;

    private Long generalId;

    private Double increaseLevel;

    private Long goalId;

    private String entityName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GlobalType getAwardType() {
        return awardType;
    }

    public void setAwardType(GlobalType awardType) {
        this.awardType = awardType;
    }

    public Long getGeneralId() {
        return generalId;
    }

    public void setGeneralId(Long generalId) {
        this.generalId = generalId;
    }

    public Double getIncreaseLevel() {
        return increaseLevel;
    }

    public void setIncreaseLevel(Double increaseLevel) {
        this.increaseLevel = increaseLevel;
    }

    public Long getGoalId() {
        return goalId;
    }

    public void setGoalId(Long goalId) {
        this.goalId = goalId;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GoalAwardDTO)) {
            return false;
        }

        return id != null && id.equals(((GoalAwardDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GoalAwardDTO{" +
            "id=" + getId() +
            ", awardType='" + getAwardType() + "'" +
            ", generalId=" + getGeneralId() +
            ", increaseLevel=" + getIncreaseLevel() +
            ", goalId=" + getGoalId() +
            "}";
    }
}
