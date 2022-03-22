package com.ivolunteer.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A DTO for the {@link com.ivolunteer.domain.Badge} entity.
 */
public class BadgeDTO implements Serializable {
    
    private Long id;

    private String name;

    private Set<GoalDTO> assignedGoals = new HashSet<>();
    
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

    public Set<GoalDTO> getAssignedGoals() {
        return assignedGoals;
    }

    public void setAssignedGoals(Set<GoalDTO> goals) {
        this.assignedGoals = goals;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BadgeDTO)) {
            return false;
        }

        return id != null && id.equals(((BadgeDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BadgeDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", assignedGoals='" + getAssignedGoals() + "'" +
            "}";
    }
}
