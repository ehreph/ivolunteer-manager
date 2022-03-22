package com.ivolunteer.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

/**
 * A ActivityProgress.
 */
@Entity
@Table(name = "competence_progress")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CompetenceProgress implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_level_change")
    private Double userLevelChange;

    @Column(name = "tracking_date")
    private Instant date;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties(value = "competenceProgress", allowSetters = true)
    private User user;

    @ManyToOne
    @JoinColumn(name = "competence_id")
    @JsonIgnoreProperties(value = "competenceProgress", allowSetters = true)
    private Competence competence;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here


    public Double getUserLevelChange() {
        return userLevelChange;
    }

    public void setUserLevelChange(Double userLevelChange) {
        this.userLevelChange = userLevelChange;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Competence getCompetence() {
        return competence;
    }

    public void setCompetence(Competence competence) {
        this.competence = competence;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CompetenceProgress)) {
            return false;
        }
        return id != null && id.equals(((CompetenceProgress) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore


    @Override
    public String toString() {
        return "CompetenceProgress{" +
            "id=" + id +
            ", userLevelChange=" + userLevelChange +
            ", date=" + date +
            ", user=" + user +
            ", competence=" + competence +
            '}';
    }
}
