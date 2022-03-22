package com.ivolunteer.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A UserGoals.
 */
@Entity
@Table(name = "user_goals")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserGoals implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "finished")
    private Boolean finished = false;

    @Column(name = "finished_date")
    private Instant finishedDate;


    @ManyToOne
    @JoinColumn(name = "goal_id")
    @JsonIgnoreProperties(value = "userGoals", allowSetters = true)
    private Goal goal;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserGoals user(User user) {
        this.user = user;
        return this;
    }

    public Boolean getFinished() {
        return finished;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }

    public Goal getGoal() {
        return goal;
    }

    public UserGoals goal(Goal goal) {
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
        if (!(o instanceof UserGoals)) {
            return false;
        }
        return id != null && id.equals(((UserGoals) o).id);
    }

    public Instant getFinishedDate() {
        return finishedDate;
    }

    public void setFinishedDate(Instant finishedDate) {
        this.finishedDate = finishedDate;
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore


    @Override
    public String toString() {
        return "UserGoals{" +
            "id=" + id +
            ", user=" + user +
            ", finished=" + finished +
            ", finishedDate=" + finishedDate +
            ", goal=" + goal +
            '}';
    }
}
