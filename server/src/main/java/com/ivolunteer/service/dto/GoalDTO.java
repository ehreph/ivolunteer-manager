package com.ivolunteer.service.dto;


import java.io.Serializable;
import java.util.List;

/**
 * A DTO for the {@link com.ivolunteer.domain.Goal} entity.
 */
public class GoalDTO implements Serializable {

  private Long id;

  private String name;

  private String info;

  private Long institutionId;

  private String institutionName;

  private Long typeId;

  private List<GoalAwardDTO> awardedCompetences;

  private List<GoalRequirementsDTO> requirements;

  private Boolean isPersonal;

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

  public String getInfo() {
    return info;
  }

  public void setInfo(String info) {
    this.info = info;
  }

  public Long getInstitutionId() {
    return institutionId;
  }

  public void setInstitutionId(Long institutionId) {
    this.institutionId = institutionId;
  }

  public Long getTypeId() {
    return typeId;
  }

  public void setTypeId(Long goalTypeId) {
    this.typeId = goalTypeId;
  }


  public Boolean getPersonal() {
    return isPersonal;
  }

  public String getInstitutionName() {
    return institutionName;
  }

  public void setInstitutionName(String institutionName) {
    this.institutionName = institutionName;
  }

  public void setPersonal(Boolean personal) {
    isPersonal = personal;
  }

  public List<GoalAwardDTO> getAwardedCompetences() {
    return awardedCompetences;
  }

  public void setAwardedCompetences(List<GoalAwardDTO> awardedCompetences) {
    this.awardedCompetences = awardedCompetences;
  }

  public List<GoalRequirementsDTO> getRequirements() {
    return requirements;
  }

  public void setRequirements(List<GoalRequirementsDTO> requirements) {
    this.requirements = requirements;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof GoalDTO)) {
      return false;
    }

    return id != null && id.equals(((GoalDTO) o).id);
  }

  @Override
  public int hashCode() {
    return 31;
  }

  // prettier-ignore

  @Override
  public String toString() {
    return "GoalDTO{" +
      "id=" + id +
      ", name='" + name + '\'' +
      ", info='" + info + '\'' +
      ", institutionId=" + institutionId +
      ", typeId=" + typeId +
      ", awardedCompetences=" + awardedCompetences +
      ", requirements=" + requirements +
      '}';
  }
}
