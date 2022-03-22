package com.ivolunteer.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

import com.ivolunteer.domain.enumeration.GlobalType;

import com.ivolunteer.domain.enumeration.RuleOperator;

/**
 * A GoalRequirements.
 */
@Entity
@Table(name = "goal_requirements")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class GoalRequirements implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private GlobalType type;

    @ManyToOne(optional = false)
    @JoinColumn(name = "general_id",nullable = false)
    private BaseData data;

    @Enumerated(EnumType.STRING)
    @Column(name = "operator")
    private RuleOperator operator;

    @Column(name = "value")
    private Double value;

    @ManyToOne
    @JoinColumn(name = "attributes_id")
    @JsonIgnoreProperties(value = "goalRequirements", allowSetters = true)
    private RuleAttribute attributes;


    // for activity  progress measuring
    @Column(name = "act_start_date")
    private Instant actStartDate;

    @Column(name = "act_end_date")
    private Instant actEndDate;



    @ManyToOne
    @JoinColumn(name = "goal_id")
    @JsonIgnoreProperties(value = "requirements", allowSetters = true)
    private Goal goal;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GlobalType getType() {
        return type;
    }

    public GoalRequirements type(GlobalType type) {
        this.type = type;
        return this;
    }

    public void setType(GlobalType type) {
        this.type = type;
    }

    public BaseData getData() {
        return data;
    }

    public void setData(BaseData data) {
        this.data = data;
    }


    public RuleOperator getOperator() {
        return operator;
    }

    public GoalRequirements operator(RuleOperator operator) {
        this.operator = operator;
        return this;
    }

    public void setOperator(RuleOperator operator) {
        this.operator = operator;
    }

    public Double getValue() {
        return value;
    }

    public GoalRequirements value(Double value) {
        this.value = value;
        return this;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public RuleAttribute getAttributes() {
        return attributes;
    }

    public GoalRequirements attributes(RuleAttribute ruleAttribute) {
        this.attributes = ruleAttribute;
        return this;
    }

    public void setAttributes(RuleAttribute ruleAttribute) {
        this.attributes = ruleAttribute;
    }

    public Goal getGoal() {
        return goal;
    }

    public GoalRequirements goal(Goal goal) {
        this.goal = goal;
        return this;
    }

    public void setGoal(Goal goal) {
        this.goal = goal;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Instant getActStartDate() {
        return actStartDate;
    }

    public void setActStartDate(Instant actStartDate) {
        this.actStartDate = actStartDate;
    }

    public Instant getActEndDate() {
        return actEndDate;
    }

    public void setActEndDate(Instant actEndDate) {
        this.actEndDate = actEndDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GoalRequirements)) {
            return false;
        }
        return id != null && id.equals(((GoalRequirements) o).id);
    }



    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GoalRequirements{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", operator='" + getOperator() + "'" +
            ", value=" + getValue() +
            "}";
    }
}
