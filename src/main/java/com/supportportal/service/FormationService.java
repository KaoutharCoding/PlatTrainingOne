package com.supportportal.service;

import com.supportportal.domain.Formation;
import javassist.NotFoundException;

import java.util.List;

public interface FormationService {

    //  Formation createFormation(Formation formation)  ;



    Formation getFormationById(Long id) throws NotFoundException;

    Formation getFormationByName(String name) throws NotFoundException;

    List<Formation> getAllFormations();


        String deleteFormation(String name) throws NotFoundException;

    Formation getFormationWithSubactivites();
    Formation findFormationyByName(String name);


    List<Formation> findAllActivitiesWithActivityName();

    String findFormationNameById(Long id);

    Formation createFormation(String name, String niveau, String desc, String type, String duree, String sousActiviteName);



    Formation updateFormation( String currentFormationName, String newName, String newNiveau, String newDescription, String newType, String newDuree, String newSousActiviteName);

    List<Formation> findAllFormationsWithSubActivityAndActivityNames();
}

