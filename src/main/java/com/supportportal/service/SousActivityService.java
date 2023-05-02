package com.supportportal.service;

import com.supportportal.domain.Activity;
import com.supportportal.domain.SousActivite;
import com.supportportal.exception.domain.EmailExistException;
import com.supportportal.exception.domain.NotAnImageFileException;
import com.supportportal.exception.domain.UserNotFoundException;
import com.supportportal.exception.domain.UsernameExistException;

import java.io.IOException;
import java.util.List;

public interface SousActivityService {


    List<SousActivite> getAllSousActivity();

    SousActivite findSousActivityByName(String name);




    SousActivite updateSousActivity(String name, SousActivite subActivity  ) throws UserNotFoundException, UsernameExistException, EmailExistException, IOException, NotAnImageFileException;

    void deleteActivity(String name) throws IOException;


    SousActivite createSubactivity(SousActivite subactivity);
}
