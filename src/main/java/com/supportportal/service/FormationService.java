package com.supportportal.service;

import com.supportportal.domain.Formation;
import com.supportportal.domain.SousActivite;
import javassist.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface FormationService {

    //  Formation createFormation(Formation formation)  ;


    Formation updateFormation(String name, String newName, String niveau, String desc, String type, String duree, String sousActiviteName, String newSubActiviteName) throws NotFoundException;

    Formation getFormationById(Long id) throws NotFoundException;

    Formation getFormationByName(String name) throws NotFoundException;

    List<Formation> getAllFormations();


        String deleteFormation(String name) throws NotFoundException;

    Formation getFormationWithSubactivites();
    Formation findFormationyByName(String name);


    List<Formation> findAllActivitiesWithActivityName();

    String findFormationNameById(Long id);

    Formation createFormation(String name, String niveau, String desc, String type, String duree, String sousActiviteName);

    List<Formation> findAllFormationsWithSubActivityName();
}

