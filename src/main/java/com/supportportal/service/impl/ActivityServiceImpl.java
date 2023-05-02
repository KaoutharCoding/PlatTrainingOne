package com.supportportal.service.impl;

import com.supportportal.domain.Activity;
import com.supportportal.exception.domain.EmailExistException;
import com.supportportal.exception.domain.NotAnImageFileException;
import com.supportportal.exception.domain.UserNotFoundException;
import com.supportportal.exception.domain.UsernameExistException;
import com.supportportal.repository.AcivityRepository;
import com.supportportal.service.ActivityService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    AcivityRepository acivityRepository;
    public ActivityServiceImpl(AcivityRepository acivityRepository) {
        this.acivityRepository = acivityRepository;
    }

    @Override
    public List<Activity> getAllActivity() {
        return acivityRepository.findAll();
    }

    @Override
    public Activity findActivityByName(String name) {
        return acivityRepository.findActivityByName(name);
    }

    public Activity createActivity(Activity activity) {
        String name = activity.getName();


        if(acivityRepository.existsByName(name)) {
            throw new IllegalArgumentException("Activity with name already exists: " + name);
        }
        return acivityRepository.save(activity);
    }


    @Override
    public Activity updateActivity(String name, String newName) throws UserNotFoundException, UsernameExistException, EmailExistException, IOException, NotAnImageFileException {
        Activity updatedActivity = findActivityByName(name);
        updatedActivity.setName(newName);
         acivityRepository.save(updatedActivity);
         return updatedActivity;
    }

    @Override
    public Activity deleteActivity(String name) throws IOException {
        Activity activity = acivityRepository.findActivityByName(name);
        acivityRepository.deleteByName(name);


        return activity;
    }

    @Override
    public Activity getActivityById(Long id) throws NotFoundException {
        return acivityRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Activity not found"));
    }
}
