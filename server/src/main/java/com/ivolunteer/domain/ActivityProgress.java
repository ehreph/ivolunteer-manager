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
@Table(name = "activity_progress")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ActivityProgress implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "value")
    private Double value;

    @Column(name = "tracking_date")
    private Instant date;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties(value = "activityProgress", allowSetters = true)
    private User user;

    @ManyToOne
    @JoinColumn(name = "activity_id")
    @JsonIgnoreProperties(value = "activityProgress", allowSetters = true)
    private Activity activity;

    @ManyToOne
    @JoinColumn(name = "attributes_id")
    @JsonIgnoreProperties(value = "activityProgress", allowSetters = true)
    private RuleAttribute attributes;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here


    public RuleAttribute getAttributes() {
        return attributes;
    }

    public void setAttributes(RuleAttribute attributes) {
        this.attributes = attributes;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
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

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ActivityProgress)) {
            return false;
        }
        return id != null && id.equals(((ActivityProgress) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore


    @Override
    public String toString() {
        return "ActivityProgress{" +
            "id=" + id +
            ", value=" + value +
            ", date=" + date +
            ", user=" + user +
            ", activity=" + activity +
            ", attributes=" + attributes +
            '}';
    }
}
