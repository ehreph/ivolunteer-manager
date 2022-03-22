package com.ivolunteer.service.dto;

import java.io.Serializable;
import java.time.Instant;

import com.ivolunteer.domain.GoalRequirements;
import com.ivolunteer.domain.enumeration.GlobalType;
import com.ivolunteer.domain.enumeration.RuleOperator;

/**
 * A DTO for the {@link GoalRequirements} entity.
 */
public class GoalRequirementsDTO implements Serializable {

  private Long id;

  private GlobalType type;

  private Long generalId;

  private RuleOperator operator;

  private Double value;

  private Long attributesId;

  private String attributesName;

  private String attributesUnitName;

  private Long goalId;

  private String entityName;

  private Instant actStartDate;

  private Instant actEndDate;


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public GlobalType getType() {
    return type;
  }

  public void setType(GlobalType type) {
    this.type = type;
  }

  public Long getGeneralId() {
    return generalId;
  }

  public void setGeneralId(Long generalId) {
    this.generalId = generalId;
  }

  public RuleOperator getOperator() {
    return operator;
  }

  public void setOperator(RuleOperator operator) {
    this.operator = operator;
  }

  public Double getValue() {
    return value;
  }

  public void setValue(Double value) {
    this.value = value;
  }

  public Long getAttributesId() {
    return attributesId;
  }

  public void setAttributesId(Long ruleAttributeId) {
    this.attributesId = ruleAttributeId;
  }

  public String getAttributesUnitName() {
    return attributesUnitName;
  }

  public void setAttributesUnitName(String attributesUnitName) {
    this.attributesUnitName = attributesUnitName;
  }

  public Long getGoalId() {
    return goalId;
  }

  public void setGoalId(Long goalId) {
    this.goalId = goalId;
  }

  public Instant getActStartDate() {
    return actStartDate;
  }

  public void setActStartDate(Instant actStartDate) {
    this.actStartDate = actStartDate;
  }

  public Instant getActEndDate() {
    return actEndDate;
  }

  public void setActEndDate(Instant actEndDate) {
    this.actEndDate = actEndDate;
  }

  public String getAttributesName() {
    return attributesName;
  }

  public void setAttributesName(String attributesName) {
    this.attributesName = attributesName;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof GoalRequirementsDTO)) {
      return false;
    }

    return id != null && id.equals(((GoalRequirementsDTO) o).id);
  }

  @Override
  public int hashCode() {
    return 31;
  }

  // prettier-ignore
  @Override
  public String toString() {
    return "GoalRequirementsDTO{" +
      "id=" + getId() +
      ", type='" + getType() + "'" +
      ", generalId='" + getGeneralId() + "'" +
      ", operator='" + getOperator() + "'" +
      ", value=" + getValue() +
      ", attributesId=" + getAttributesId() +
      ", goalId=" + getGoalId() +
      "}";
  }

  public void setEntityName(String entityName) {
    this.entityName = entityName;
  }

  public String getEntityName() {
    return entityName;
  }
}
