package com.ivolunteer.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.ivolunteer.domain.Competence} entity.
 */
public class ActivityDTO implements Serializable {

    private Long id;

    private String name;

    private Long attributesId;
    private String attributesName;
    private String attributesUnitName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getAttributesId() {
        return attributesId;
    }

    public void setAttributesId(Long attributesId) {
        this.attributesId = attributesId;
    }

    public String getAttributesName() {
        return attributesName;
    }

    public void setAttributesName(String attributesName) {
        this.attributesName = attributesName;
    }

    public String getAttributesUnitName() {
        return attributesUnitName;
    }

    public void setAttributesUnitName(String attributesUnitName) {
        this.attributesUnitName = attributesUnitName;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ActivityDTO)) {
            return false;
        }

        return id != null && id.equals(((ActivityDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore

    @Override
    public String toString() {
        return "ActivityDTO{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", attributesId=" + attributesId +
            '}';
    }
}
