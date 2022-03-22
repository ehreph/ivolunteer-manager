package com.ivolunteer.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Institution.
 */
@Entity
@Table(name = "institution")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Institution implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "institution")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Goal> institutionGoals = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Institution name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Institution description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Goal> getInstitutionGoals() {
        return institutionGoals;
    }

    public Institution institutionGoals(Set<Goal> goals) {
        this.institutionGoals = goals;
        return this;
    }

    public Institution addInstitutionGoals(Goal goal) {
        this.institutionGoals.add(goal);
        goal.setInstitution(this);
        return this;
    }

    public Institution removeInstitutionGoals(Goal goal) {
        this.institutionGoals.remove(goal);
        goal.setInstitution(null);
        return this;
    }

    public void setInstitutionGoals(Set<Goal> goals) {
        this.institutionGoals = goals;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Institution)) {
            return false;
        }
        return id != null && id.equals(((Institution) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Institution{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
