package com.supportportal.service;

import com.supportportal.domain.Activity;
import com.supportportal.exception.domain.*;
import javassist.NotFoundException;

import java.io.IOException;
import java.util.List;

public interface ActivityService {


    List<Activity> getAllActivity();

    Activity findActivityByName(String name);


    Activity createActivity(Activity activity);

    Activity updateActivity(String name, String newName ) throws UserNotFoundException, UsernameExistException, EmailExistException, IOException, NotAnImageFileException;

    Activity deleteActivity(String name) throws IOException;


    Activity getActivityById(Long activityId) throws NotFoundException;
}
