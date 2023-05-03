package com.supportportal.service.impl;

import com.supportportal.domain.Activity;
import com.supportportal.domain.Formation;
import com.supportportal.exception.domain.FormationNameAlreadyExistsException;
import com.supportportal.repository.FormationRepository;
import com.supportportal.service.FormationService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class FormationServiceImpl implements FormationService {
    @Autowired
    private FormationRepository formationRepository;

    public FormationServiceImpl(FormationRepository formationRepository) {
        this.formationRepository = formationRepository;
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
    public Formation createFormation(@RequestParam("name") String name,
                                     @RequestParam("niveau") String niveau,
                                     @RequestParam("desc") String desc,
                                     @RequestParam("type") String type,
                                     @RequestParam("duree") String duree) throws FormationNameAlreadyExistsException {




        Formation formation = new Formation();
        String nameFound = formation.getName();
        if (formationRepository.existsByname(nameFound) ) {
            throw new IllegalArgumentException("Formation with name '" + name + "' already exists.");
        }
        formation.setName(name);
        formation.setNiveau(niveau);
        formation.setDescription(desc);
        formation.setType(type);
        formation.setDuree(duree);

        return formationRepository.save(formation);
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

    public List<Formation> getAllFormations() {
        return formationRepository.findAll();
    }

    public String deleteFormation(String name) throws NotFoundException {
        Formation formation = getFormationByName(name);
        if(!formationRepository.existsByname(name) ) {
            throw new IllegalArgumentException("Formation with name '" + name + "' doesn't exists.");
        }
        formationRepository.delete(formation);
        return "Activity with name " + name + " has been deleted successfully.";
    }
    // Other methods for updating, deleting, etc.
}

