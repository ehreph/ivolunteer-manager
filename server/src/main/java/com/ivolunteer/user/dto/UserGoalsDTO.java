package com.ivolunteer.user.dto;


import java.io.Serializable;

/**
 * A DTO for the {@link com.ivolunteer.domain.UserGoals} entity.
 */
public class UserGoalsDTO implements Serializable {


    public UserGoalsDTO() {
    }

    public UserGoalsDTO(Long id, Long userId, Long goalId, String goalName, Boolean finished) {
        this.id = id;
        this.userId = userId;
        this.goalId = goalId;
        this.goalName = goalName;
        this.finished = finished;
    }

    private Long id;

    private Long userId;

    private Long goalId;

    private String goalName;

    private Boolean finished;

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

    public Long getGoalId() {
        return goalId;
    }

    public void setGoalId(Long goalId) {
        this.goalId = goalId;
    }

    public String getGoalName() {
        return goalName;
    }

    public void setGoalName(String goalName) {
        this.goalName = goalName;
    }


    public Boolean getFinished() {
        return finished;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserGoalsDTO)) {
            return false;
        }

        return id != null && id.equals(((UserGoalsDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore

    @Override
    public String toString() {
        return "UserGoalsDTO{" +
            "id=" + id +
            ", userId=" + userId +
            ", goalId=" + goalId +
            ", goalName='" + goalName + '\'' +
            ", finished=" + finished +
            '}';
    }
}
