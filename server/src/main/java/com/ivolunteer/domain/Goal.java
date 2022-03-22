package com.ivolunteer.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A Goal.
 */
@Entity
@DiscriminatorValue("goal")
public class Goal extends BaseData implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "info" ,columnDefinition = "CLOB")
    private String info;

    @OneToMany(mappedBy = "goal", cascade = {CascadeType.PERSIST, CascadeType.MERGE,
        CascadeType.REFRESH}, orphanRemoval = true)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private List<GoalAward> awardedCompetences = new ArrayList<>();

    @OneToMany(mappedBy = "goal", cascade = {CascadeType.PERSIST, CascadeType.MERGE,
        CascadeType.REFRESH}, orphanRemoval = true)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private List<GoalRequirements> requirements = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "institution_id")
    @JsonIgnoreProperties(value = "institutionGoals", allowSetters = true)
    private Institution institution;

    @ManyToOne
    @JoinColumn(name = "created_by_user_id")
    private User createdByUser;

    // should never be null but constraint cant be set because of inheritence
    @Column(name = "is_personal")
    private Boolean isPersonal;


    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Goal name(String name) {
        this.setName(name);
        return this;
    }
    public String getInfo() {
        return info;
    }

    public Goal info(String info) {
        this.info = info;
        return this;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public List<GoalAward> getAwardedCompetences() {
        return awardedCompetences;
    }

    public Goal awardedCompetences(List<GoalAward> goalAwards) {
        this.awardedCompetences = goalAwards;
        return this;
    }

    public Goal addAwardedCompetence(GoalAward goalAward) {
        this.awardedCompetences.add(goalAward);
        goalAward.setGoal(this);
        return this;
    }

    public Goal removeAwardedCompetence(GoalAward goalAward) {
        this.awardedCompetences.remove(goalAward);
        goalAward.setGoal(null);
        return this;
    }

    public void setAwardedCompetences(List<GoalAward> goalAwards) {
        this.awardedCompetences = goalAwards;
    }

    public List<GoalRequirements> getRequirements() {
        return requirements;
    }

    public Goal requirements(List<GoalRequirements> goalRequirements) {
        this.requirements = goalRequirements;
        return this;
    }

    public Goal addRequirements(GoalRequirements goalRequirements) {
        this.requirements.add(goalRequirements);
        goalRequirements.setGoal(this);
        return this;
    }

    public Goal removeRequirements(GoalRequirements goalRequirements) {
        this.requirements.remove(goalRequirements);
        goalRequirements.setGoal(null);
        return this;
    }

    public void setRequirements(List<GoalRequirements> goalRequirements) {
        this.requirements = goalRequirements;
    }

    public Institution getInstitution() {
        return institution;
    }

    public Goal institution(Institution institution) {
        this.institution = institution;
        return this;
    }

    public void setInstitution(Institution institution) {
        this.institution = institution;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public int hashCode() {
        return 31;
    }


    public User getCreatedByUser() {
        return createdByUser;
    }

    public void setCreatedByUser(User createdByUser) {
        this.createdByUser = createdByUser;
    }

    public Boolean getIsPersonal() {
        return isPersonal;
    }

    public void setIsPersonal(Boolean isPersonal) {
        this.isPersonal = isPersonal;
    }

    // prettier-ignore

    @Override
    public String toString() {
        return "Goal{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", info='" + info + '\'' +
            ", awardedCompetences=" + awardedCompetences +
            ", requirements=" + requirements +
            ", institution=" + institution +
            ", createdByUser=" + createdByUser +
            ", isPersonal=" + isPersonal +
            '}';
    }
}
