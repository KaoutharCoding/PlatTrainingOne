package com.supportportal.domain;

import java.beans.Transient;

public class SubactivityRequestDTO {
    private String name;
  //  private Long activityId;

  private String activityName;

    public SubactivityRequestDTO() {
    }

    public SubactivityRequestDTO(String name,String activityName) {
        this.name = name;
        //this.activityId = activityId;
        this.activityName=activityName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }
}
