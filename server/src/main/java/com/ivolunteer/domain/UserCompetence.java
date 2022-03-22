package com.ivolunteer.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A UserCompetence.
 */
@Entity
@Table(name = "user_competence")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserCompetence implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "user_level")
    private Double userLevel;

    @ManyToOne(optional = false)
    @JoinColumn(name = "comp_id", nullable = false)
    @JsonIgnoreProperties(value = "userCompetences", allowSetters = true)
    private Competence comp;

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
    public UserCompetence user(User user) {
        this.user = user;
        return this;
    }

    public Double getUserLevel() {
        return userLevel;
    }

    public UserCompetence userLevel(Double userLevel) {
        this.userLevel = userLevel;
        return this;
    }

    public void setUserLevel(Double userLevel) {
        this.userLevel = userLevel;
    }

    public Competence getComp() {
        return comp;
    }

    public UserCompetence comp(Competence competence) {
        this.comp = competence;
        return this;
    }

    public void setComp(Competence competence) {
        this.comp = competence;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserCompetence)) {
            return false;
        }
        return id != null && id.equals(((UserCompetence) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "UserCompetence{" +
            "id=" + id +
            ", user=" + user +
            ", userLevel=" + userLevel +
            ", comp=" + comp +
            '}';
    }
}
