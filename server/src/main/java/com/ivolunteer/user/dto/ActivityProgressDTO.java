package com.ivolunteer.user.dto;

import java.io.Serializable;
import java.time.Instant;

/**
 * A DTO for the {@link com.ivolunteer.domain.Competence} entity.
 */
public class ActivityProgressDTO implements Serializable {

    private Long id;
    private Long activityId;

    private Double value;
    private Instant date;
    private Long attributesId;
    private Long userId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
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

    public Long getAttributesId() {
        return attributesId;
    }

    public void setAttributesId(Long attributesId) {
        this.attributesId = attributesId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ActivityProgressDTO)) {
            return false;
        }

        return id != null && id.equals(((ActivityProgressDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore


    @Override
    public String toString() {
        return "ActivityProgressDTO{" +
            "id=" + id +
            ", activityid=" + activityId +
            ", value=" + value +
            ", date=" + date +
            ", attributesId=" + attributesId +
            ", userId=" + userId +
            '}';
    }
}
