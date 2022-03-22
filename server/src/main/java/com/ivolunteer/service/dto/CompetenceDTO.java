package com.ivolunteer.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.ivolunteer.domain.Competence} entity.
 */
public class CompetenceDTO implements Serializable {
    
    private Long id;

    private String name;

    private Double maxLevel;

    
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

    public Double getMaxLevel() {
        return maxLevel;
    }

    public void setMaxLevel(Double maxLevel) {
        this.maxLevel = maxLevel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CompetenceDTO)) {
            return false;
        }

        return id != null && id.equals(((CompetenceDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CompetenceDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", maxLevel=" + getMaxLevel() +
            "}";
    }
}
