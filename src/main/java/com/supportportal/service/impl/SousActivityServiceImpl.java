package com.supportportal.service.impl;

import com.supportportal.domain.Activity;
import com.supportportal.domain.SousActivite;
import com.supportportal.exception.domain.EmailExistException;
import com.supportportal.exception.domain.NotAnImageFileException;
import com.supportportal.exception.domain.UserNotFoundException;
import com.supportportal.exception.domain.UsernameExistException;
import com.supportportal.repository.SousAcivityRepository;
import com.supportportal.service.SousActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.util.List;

@Service
public class SousActivityServiceImpl implements SousActivityService {

    @Autowired
    SousAcivityRepository sousAcivityRepository;

    public SousActivityServiceImpl(SousAcivityRepository acivityRepository) {
        this.sousAcivityRepository = acivityRepository;
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
    public SousActivite updateSousActivity(String name, SousActivite updatedSubactivity) {
        SousActivite subactivity = findSousActivityByName(name);
        subactivity.setName(updatedSubactivity.getName());
        return sousAcivityRepository.save(subactivity);
    }

    @Override
    public void deleteActivity(String name) throws IOException {
        SousActivite subActivite = findSousActivityByName(name);
        sousAcivityRepository.delete(subActivite);


    }

    @Override
    public SousActivite createSubactivity(SousActivite subactivity) {
        return sousAcivityRepository.save(subactivity);
    }
}
