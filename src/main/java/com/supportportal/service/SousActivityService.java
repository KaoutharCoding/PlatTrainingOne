package com.supportportal.service;

import com.supportportal.domain.SousActivite;
import com.supportportal.domain.SubactivityRequestDTO;

import java.io.IOException;
import java.util.List;

public interface SousActivityService {


    List<SousActivite> getAllSousActivity();

    SousActivite findSousActivityByName(String name);

    SousActivite updateSubactivity(String subactivityName, String newName);

    void deleteSubActivity(String name) throws IOException;


   // SousActivite updateSubactivity(String name, SubactivityRequestDTO requestDTO);

    void deleteSubActivityAll() throws IOException;

    SousActivite createSubactivity(SubactivityRequestDTO requestDTO);
}
