package com.supportportal.domain;

public class SubactivityRequestDTO {
    private String name;
    private Long activityId;


    public SubactivityRequestDTO() {
    }

    public SubactivityRequestDTO(String name, Long activityId) {
        this.name = name;
        this.activityId = activityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }
}
