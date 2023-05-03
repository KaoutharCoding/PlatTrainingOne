package com.supportportal.service.impl;

import com.supportportal.domain.Activity;
import com.supportportal.domain.SousActivite;
import com.supportportal.domain.SubactivityRequestDTO;
import com.supportportal.repository.SousAcivityRepository;
import com.supportportal.service.ActivityService;
import com.supportportal.service.SousActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class SousActivityServiceImpl implements SousActivityService {

    @Autowired
    SousAcivityRepository sousAcivityRepository;

    @Autowired
    private ActivityService activityService;

    public SousActivityServiceImpl(SousAcivityRepository sousAcivityRepository, ActivityService activityService) {
        this.sousAcivityRepository = sousAcivityRepository;
        this.activityService = activityService;
    }

    @Override
    public List<SousActivite> getAllSousActivity() {
        return sousAcivityRepository.findAll();
    }

    @Override
    public SousActivite findSousActivityByName(String name) {
        return sousAcivityRepository.findSousActivityByName(name);
    }



    @Override
    public SousActivite createSubactivity(SousActivite subactivity) {
        String name = subactivity.getName();
        if(sousAcivityRepository.existsByName(name)) {
            throw new IllegalArgumentException("SubActivity with name " + name +" already exists: " );
        }
        return sousAcivityRepository.save(subactivity);
    }

    @Override
    public SousActivite updateSousActivity(String name, Long activityId) throws IOException {
        SousActivite subactivity = findSousActivityByName(name);

        if (subactivity == null) {
            throw new IllegalArgumentException("SubActivity not found with name: " + name);
        }

        subactivity.setName(name);

        // Retrieve the activity by its ID
        Activity activity = activityService.findActivityById(activityId);
        if (activity == null) {
            throw new IllegalArgumentException("Activity not found with ID: " + activityId);
        }
        subactivity.setActivity(activity);

        return sousAcivityRepository.save(subactivity);
    }




    @Override
    public void deleteSubActivity(String name) throws IOException {
        SousActivite subActivite = findSousActivityByName(name);
        sousAcivityRepository.delete(subActivite);


    }

}
