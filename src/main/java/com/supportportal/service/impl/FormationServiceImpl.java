package com.supportportal.service.impl;

import com.supportportal.domain.Activity;
import com.supportportal.domain.Formation;
import com.supportportal.domain.SousActivite;
import com.supportportal.repository.FormationRepository;
import com.supportportal.service.FormationService;
import com.supportportal.service.SousActivityService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class FormationServiceImpl implements FormationService {
    @Autowired
    private FormationRepository formationRepository;

    @Autowired
    private SousActivityService sousActivityService;

    public FormationServiceImpl(FormationRepository formationRepository) {
        this.formationRepository = formationRepository;
    }

    @Override
    public List<Formation> findAllActivitiesWithActivityName() {
        List<Formation> formations = getAllFormations();
        for (Formation formation : formations) {
            if (formation.getSousActivite() != null) {
                String sousActivityName = findFormationNameById(formation.getSousActivite().getId());
                formation.setSubActivityName(sousActivityName);
            }
        }
        return formations;
    }

    @Override
    public String findFormationNameById(Long id) {
        Optional<Formation> formationOptional = formationRepository.findById(id);
        if (formationOptional.isPresent()) {
            Formation formation = formationOptional.get();
            return formation.getName();
        } else {
            throw new IllegalArgumentException("Formtaion not found with id: " + id);
        }
    }



    // @Override
   /* public Formation createFormation(Formation formation) throws FormationNameAlreadyExistsException {
        String name = formation.getName();
        Formation existingFormation = formationRepository.findByName(name);
        if (existingFormation != null) {
            throw new FormationNameAlreadyExistsException("Formation with name '" + name + "' already exists.");
        }
        return formationRepository.save(formation);
    }
*/



    @Override
    public Formation createFormation(String name, String niveau, String desc, String type, String duree, String sousActiviteName) {
        // Check if a formation with the same name already exists
        if (formationRepository.existsByname(name)) {
            throw new IllegalArgumentException("Formation with name '" + name + "' already exists.");
        }

        // Get the subactivity by name
        SousActivite sousActivite = sousActivityService.findSousActivityByName(sousActiviteName);
        if (sousActivite == null) {
            throw new IllegalArgumentException("SubActivity not found with name: " + sousActiviteName);
        }
        Activity a =sousActivite.getActivity();

        // Create a new Formation object and set its properties
        Formation formation = new Formation();
        formation.setName(name);
        formation.setNiveau(niveau);
        formation.setDescription(desc);
        formation.setType(type);
        formation.setDuree(duree);
        formation.setSousActivite(sousActivite);
        sousActivite.setActivityName(a.getName());
        formation.setSubActivityName(sousActiviteName);

        return formationRepository.save(formation);
    }

@Override
    public List<Formation> findAllFormationsWithSubActivityName() {
        List<Formation> formations = getAllFormations();

        for (Formation formation : formations) {
            if (formation.getSousActivite() != null) {
                String activityName = findFormationNameById(formation.getSousActivite().getId());
                formation.setSubActivityName(activityName);
                SousActivite s = formation.getSousActivite();
                s.setActivityName(formation.getSousActivite().getActivity().getName());

              //  s.setActivity(formation.getSousActivite().getActivity());
                formation.setSubActivityName(activityName);
               // findAllActivitiesWithActivityName();
                formation.setSousActivite(formation.getSousActivite());
                formation.setActivityName(formation.getSousActivite().getActivityName());

            }
        }
        return formations;
    }

    @Override
    public Formation updateFormation(String name, String newName, String niveau, String desc, String type, String duree, String sousActiviteName, String newSubActiviteName) throws NotFoundException {
        Formation existingFormation = formationRepository.findByName(name);
        if (existingFormation == null) {
            throw new IllegalArgumentException("Formation not found with name: " + name);
        }
        SousActivite sousActivite = sousActivityService.findSousActivityByName(sousActiviteName);
        if (sousActivite == null) {
            throw new IllegalArgumentException("SubActivity not found with name: " + sousActiviteName);
        }
        Activity a = sousActivite.getActivity();

        // fetch the new SousActivite object for the new subActivityName
        SousActivite newSousActivite = sousActivityService.findSousActivityByName(newSubActiviteName);
        if (newSousActivite == null) {
            throw new IllegalArgumentException("SubActivity not found with name: " + newSubActiviteName);
        }

        existingFormation.setName(newName);
        existingFormation.setNiveau(niveau);
        existingFormation.setDescription(desc);
        existingFormation.setType(type);
        existingFormation.setDuree(duree);

        existingFormation.setSousActivite(newSousActivite); // set the new SousActivite object
        newSousActivite.setActivityName(a.getName());
        existingFormation.setActivityName(existingFormation.getActivityName());
        existingFormation.setSubActivityName(newSubActiviteName);
        formationRepository.save(existingFormation);
        return existingFormation;
    }



    @Override
    public Formation getFormationById(Long id) throws NotFoundException {
        return formationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Formation not found with id: " + id));
    }

    @Override
    public Formation getFormationByName(String name) throws NotFoundException {

        return formationRepository.findByName(name);
    }



   @Override
    public List<Formation> getAllFormations() {
        return formationRepository.findAll();
    }

    @Override
    @Transactional
    public String deleteFormation(String name) throws NotFoundException {
        Formation formation = getFormationByName(name);
        formationRepository.delete(formation);
        return "formation with name " + name + " has been deleted successfully.";
    }

  //  @Override
    public Formation getFormationWithSubactivites() {
        return null;
    }

    @Override
    public Formation findFormationyByName(String name) {
        return null;
    }


}

