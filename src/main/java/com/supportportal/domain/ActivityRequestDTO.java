package com.supportportal.domain;

public class ActivityRequestDTO {
    private String name;

    public ActivityRequestDTO() {
    }

    public ActivityRequestDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
