package com.ivolunteer.user.dto;

import java.util.List;

public class UserActivityDTO {

//    private Long id;

  private Long userId;

  private Long activityId;

  private String name;

  private String attributeName;

  private String attributeUnitName;


  private Double progressAmount;


  public UserActivityDTO() {
  }

//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public Long getActivityId() {
    return activityId;
  }

  public void setActivityId(Long activityId) {
    this.activityId = activityId;
  }


  public void setProgressAmount(Double progressAmount) {
    this.progressAmount = progressAmount;
  }

  public Double getProgressAmount() {
    return progressAmount;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAttributeName() {
    return attributeName;
  }

  public void setAttributeName(String attributeName) {
    this.attributeName = attributeName;
  }

  public String getAttributeUnitName() {
    return attributeUnitName;
  }

  public void setAttributeUnitName(String attributeUnitName) {
    this.attributeUnitName = attributeUnitName;
  }
}
