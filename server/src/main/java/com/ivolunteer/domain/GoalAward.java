package com.ivolunteer.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

import com.ivolunteer.domain.enumeration.GlobalType;

/**
 * A GoalAward.
 */
@Entity
@Table(name = "goal_award")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class GoalAward implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "award_type")
    private GlobalType awardType;

    @ManyToOne
    @JoinColumn(name = "general_id")
    private BaseData data;

    @Column(name = "increase_level")
    private Double increaseLevel;

    @ManyToOne
    @JoinColumn(name = "goal_id")
    @JsonIgnoreProperties(value = "awardedCompetences", allowSetters = true)
    private Goal goal;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GlobalType getAwardType() {
        return awardType;
    }

    public GoalAward awardType(GlobalType awardType) {
        this.awardType = awardType;
        return this;
    }

    public void setAwardType(GlobalType awardType) {
        this.awardType = awardType;
    }

    public BaseData getData() {
        return data;
    }

    public void setData(BaseData data) {
        this.data = data;
    }

    public Double getIncreaseLevel() {
        return increaseLevel;
    }

    public GoalAward increaseLevel(Double increaseLevel) {
        this.increaseLevel = increaseLevel;
        return this;
    }

    public void setIncreaseLevel(Double increaseLevel) {
        this.increaseLevel = increaseLevel;
    }

    public Goal getGoal() {
        return goal;
    }

    public GoalAward goal(Goal goal) {
        this.goal = goal;
        return this;
    }

    public void setGoal(Goal goal) {
        this.goal = goal;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GoalAward)) {
            return false;
        }
        return id != null && id.equals(((GoalAward) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "GoalAward{" +
            "id=" + id +
            ", awardType=" + awardType +
            ", data=" + data +
            ", increaseLevel=" + increaseLevel +
            ", goal=" + goal +
            '}';
    }
}
