package com.ivolunteer.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.ivolunteer.domain.UserBadge} entity.
 */
public class UserBadgeDTO implements Serializable {

    private Long id;

    private Long userId;

    private Long badgeId;

    private String badgeName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getBadgeId() {
        return badgeId;
    }

    public void setBadgeId(Long badgeId) {
        this.badgeId = badgeId;
    }

    public String getBadgeName() {
        return badgeName;
    }

    public void setBadgeName(String badgeName) {
        this.badgeName = badgeName;
    }

    public UserBadgeDTO(Long id, Long userId, Long badgeId, String badgeName) {
        this.id = id;
        this.userId = userId;
        this.badgeId = badgeId;
        this.badgeName = badgeName;
    }

    public UserBadgeDTO() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserBadgeDTO)) {
            return false;
        }

        return id != null && id.equals(((UserBadgeDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserBadgeDTO{" +
            "id=" + getId() +
            ", userId=" + getUserId() +
            ", badgeId=" + getBadgeId() +
            "}";
    }
}
