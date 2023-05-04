package com.supportportal.service;

import com.supportportal.domain.Activity;
import com.supportportal.domain.ActivityRequestDTO;
import javassist.NotFoundException;

import java.io.IOException;
import java.util.List;

public interface ActivityService {


    Activity getActivityWithRelatedActivities(String activityId);

    List<Activity> getAllActivity();

    Activity findActivityByName(String name);


    Activity createActivity(Activity activity);


    Activity updateActivity(String name, ActivityRequestDTO requestDTO) throws NotFoundException;


    String deleteActivity(String name) throws IOException;


    Activity getActivityById(Long activityId) throws NotFoundException;

    Activity findActivityById(Long activityId);
}
