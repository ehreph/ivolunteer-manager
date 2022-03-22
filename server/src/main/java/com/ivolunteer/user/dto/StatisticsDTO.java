package com.ivolunteer.user.dto;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class StatisticsDTO {

    private Long userId;
    private String firstName;
    private String lastName;
    private Long finishedGoals = 0l;
    private Long openGoals = 0l;
    private Long goals = 0l;
    private Map<Instant, Long> finishedGoalsPerMonth = new HashMap<>();
    private Map<Instant, Long> finishedGoalsPerDate = new HashMap<>();
    private Map<String, Double> levelPerCompetence = new HashMap<>();

    public StatisticsDTO() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Long getFinishedGoals() {
        return finishedGoals;
    }

    public void setFinishedGoals(Long finishedGoals) {
        this.finishedGoals = finishedGoals;
    }

    public Long getOpenGoals() {
        return openGoals;
    }

    public void setOpenGoals(Long openGoals) {
        this.openGoals = openGoals;
    }

    public Long getGoals() {
        return goals;
    }

    public void setGoals(Long goals) {
        this.goals = goals;
    }

    public Map<Instant, Long> getFinishedGoalsPerMonth() {
        return finishedGoalsPerMonth;
    }

    public void setFinishedGoalsPerMonth(Map<Instant, Long> finishedGoalsPerMonth) {
        this.finishedGoalsPerMonth = finishedGoalsPerMonth;
    }

    public Map<Instant, Long> getFinishedGoalsPerDate() {
        return finishedGoalsPerDate;
    }

    public void setFinishedGoalsPerDate(Map<Instant, Long> finishedGoalsPerDate) {
        this.finishedGoalsPerDate = finishedGoalsPerDate;
    }

    public Map<String, Double> getLevelPerCompetence() {
        return levelPerCompetence;
    }

    public void setLevelPerCompetence(Map<String, Double> levelPerCompetence) {
        this.levelPerCompetence = levelPerCompetence;
    }

    @Override
    public String toString() {
        return "StatisticsDTO{" +
            "userId=" + userId +
            ", finishedGoals=" + finishedGoals +
            ", openGoals=" + openGoals +
            ", goals=" + goals +
            ", finishedGoalsPerMonth=" + finishedGoalsPerMonth +
            ", finishedGoalsPerDate=" + finishedGoalsPerDate +
            ", competencePerLevel=" + levelPerCompetence +
            '}';
    }
}
