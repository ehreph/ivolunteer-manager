package com.ivolunteer.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A UserBadge.
 */
@Entity
@Table(name = "user_badge")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserBadge implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "finished_date")
    private Instant finishedDate;

    @ManyToOne(optional = false)
    @JoinColumn(name = "badge_id", nullable = false)
    @JsonIgnoreProperties(value = "userBadges", allowSetters = true)
    private Badge badge;

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
    public UserBadge user(User user) {
        this.user = user;
        return this;
    }

    public Badge getBadge() {
        return badge;
    }

    public UserBadge badge(Badge badge) {
        this.badge = badge;
        return this;
    }

    public void setBadge(Badge badge) {
        this.badge = badge;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserBadge)) {
            return false;
        }
        return id != null && id.equals(((UserBadge) o).id);
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

    @Override
    public String toString() {
        return "UserBadge{" +
            "id=" + id +
            ", user=" + user +
            ", finishedDate=" + finishedDate +
            ", badge=" + badge +
            '}';
    }
}
