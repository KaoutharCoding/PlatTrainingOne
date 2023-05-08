package com.supportportal.service.impl;

import com.supportportal.domain.Activity;
import com.supportportal.domain.Formation;
import com.supportportal.domain.SousActivite;
import com.supportportal.repository.FormationRepository;
import com.supportportal.service.FormationService;
import com.supportportal.service.SousActivityService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
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
        formation.setSubActivityName(formation.getSousActivite().getActivityName());
        formation.setSousActivite(sousActivite);
        formation.setActivityName(formation.getSousActivite().getActivityName());
        formation.setActivityName(formation.getSousActivite().getActivity().getName());


        return formationRepository.save(formation);
    }

    @Override
    public Formation updateFormation(String currentFormationName, String newName, String newNiveau, String newDescription, String newType, String newDuree, String newSousActiviteName) {

        Formation existingFormation = formationRepository.findByName(currentFormationName);
        if (existingFormation == null) {
            throw new IllegalArgumentException("Formation not found with name: " + currentFormationName);
        }

        // fetch the new SousActivite object for the new subActivityName
        SousActivite newSousActivite = sousActivityService.findSousActivityByName(newSousActiviteName);
        if (newSousActivite == null) {
            throw new IllegalArgumentException("SubActivity not found with name: " + newSousActiviteName);
        }

        // update the existing Formation object with the new details
        existingFormation.setName(newName);
        existingFormation.setNiveau(newNiveau);
        existingFormation.setDescription(newDescription);
        existingFormation.setType(newType);
        existingFormation.setDuree(newDuree);
        existingFormation.setSousActivite(newSousActivite);
        newSousActivite.setActivityName(newSousActivite.getActivity().getName());

        existingFormation.setActivityName(newSousActivite.getActivity().getName());
        existingFormation.setSubActivityName(newSousActivite.getName());


        // save the updated Formation object
        formationRepository.save(existingFormation);

        return existingFormation;
    }




    //   @Override
   /* public Formation updateFormation(String currentFormationName, Formation formation) {

        Formation existingFormation = formationRepository.findByName(formation.getName());
        if (existingFormation == null) {
            throw new IllegalArgumentException("Formation not found with name: " + formation.getName());
        }
        SousActivite sousActivite = sousActivityService.findSousActivityByName(formation.getSubActivityName());
        if (sousActivite == null) {
            throw new IllegalArgumentException("SubActivity not found with name: " + formation.getSubActivityName());
        }
        Activity a = sousActivite.getActivity();


        existingFormation.setName(formation.getName());
        existingFormation.setNiveau(formation.getNiveau());
        existingFormation.setDescription(formation.getDescription());
        existingFormation.setType(formation.getType());
        existingFormation.setDuree(formation.getDuree());

        existingFormation.setSousActivite(formation.getSousActivite()); // set the new SousActivite object
        existingFormation.setActivityName(existingFormation.getActivityName());
        existingFormation.setSubActivityName(formation.getSubActivityName());
        formationRepository.save(existingFormation);
        return existingFormation;
    }
*/

    @Override
 public List<Formation> findAllFormationsWithSubActivityAndActivityNames() {
 List<Formation> formations = formationRepository.findAll();
 for (Formation formation : formations) {
 if (formation.getSousActivite() != null) {
 String subActivityName = formation.getSousActivite().getName();
 formation.setSubActivityName(subActivityName);
 if (formation.getSousActivite().getActivity() != null) {

   //  String activityName = formation.getSousActivite().getActivity().getName();
     formation.setSubActivityName(formation.getSubActivityName());
     formation.getSousActivite().setActivityName(formation.getSousActivite().getActivity().getName());

     formation.setActivityName(formation.getSousActivite().getActivityName());
     formation.setSubActivityName(formation.getSousActivite().getName());

     //formation.setActivityName(formation.getSousActivite().getActivityName());


 }
 }

}
        return formations;

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

