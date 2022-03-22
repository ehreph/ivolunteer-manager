package com.ivolunteer.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.ivolunteer.domain.UserCompetence} entity.
 */
public class UserCompetenceDTO implements Serializable {

    private Long id;

    private Long userId;

    private Double userLevel;

    private Long compId;
    private String compName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Double getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(Double userLevel) {
        this.userLevel = userLevel;
    }

    public Long getCompId() {
        return compId;
    }

    public void setCompId(Long competenceId) {
        this.compId = competenceId;
    }


    public String getCompName() {
        return compName;
    }

    public void setCompName(String compName) {
        this.compName = compName;
    }

    public UserCompetenceDTO() {
    }

    public UserCompetenceDTO(Long id, Long userId, Double userLevel, Long compId, String compName) {
        this.id = id;
        this.userId = userId;
        this.userLevel = userLevel;
        this.compId = compId;
        this.compName = compName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserCompetenceDTO)) {
            return false;
        }

        return id != null && id.equals(((UserCompetenceDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserCompetenceDTO{" +
            "id=" + getId() +
            ", userId=" + getUserId() +
            ", userLevel=" + getUserLevel() +
            ", compId=" + getCompId() +
            "}";
    }
}
