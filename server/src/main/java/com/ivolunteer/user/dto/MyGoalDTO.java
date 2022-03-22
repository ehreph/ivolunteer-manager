package com.ivolunteer.user.dto;

import com.ivolunteer.domain.UserGoals;
import com.ivolunteer.service.dto.GoalDTO;
import com.ivolunteer.service.dto.UserBadgeDTO;
import com.ivolunteer.service.dto.UserCompetenceDTO;

import java.io.Serializable;
import java.util.List;

/**
 * A DTO for the {@link UserGoals} entity.
 */
public class MyGoalDTO implements Serializable {

    private Long id;

    private Long userId;

    private GoalDTO goal;

    private List<UserCompetenceDTO> userCompetences;
    private List<UserBadgeDTO> userBadges;
    private List<UserGoalsDTO> userGoals;
    private List<UserActivityDTO> userActivities;

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

    public GoalDTO getGoal() {
        return goal;
    }

    public void setGoal(GoalDTO goal) {
        this.goal = goal;
    }

    public List<UserCompetenceDTO> getUserCompetences() {
        return userCompetences;
    }

    public void setUserCompetences(List<UserCompetenceDTO> userCompetences) {
        this.userCompetences = userCompetences;
    }


    public List<UserGoalsDTO> getUserGoals() {
        return userGoals;
    }

    public void setUserGoals(List<UserGoalsDTO> userGoals) {
        this.userGoals = userGoals;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MyGoalDTO)) {
            return false;
        }

        return id != null && id.equals(((MyGoalDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserGoalsDTO{" +
            "id=" + getId() +
            ", userId=" + getUserId() +
            "}";
    }

    public void setUserBadges(List<UserBadgeDTO> userBadges) {
        this.userBadges = userBadges;
    }

    public List<UserBadgeDTO> getUserBadges() {
        return userBadges;
    }

    public void setUserActivities(List<UserActivityDTO> userActivities) {
        this.userActivities = userActivities;
    }

    public List<UserActivityDTO> getUserActivities() {
        return userActivities;
    }
}
