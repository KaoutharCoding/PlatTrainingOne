package com.supportportal.service;

import com.supportportal.domain.SousActivite;
import com.supportportal.domain.SubactivityRequestDTO;

import java.io.IOException;
import java.util.List;

public interface SousActivityService {


    List<SousActivite> getAllSousActivity();

    SousActivite findSousActivityByName(String name);



    SousActivite createSubactivity(SousActivite subactivity);



    SousActivite updateSousActivity(String name, Long activityId) throws IOException;

    void deleteSubActivity(String name) throws IOException;
}
