package com.supportportal.service;

import com.supportportal.domain.Formation;
import javassist.NotFoundException;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface FormationService {

    Formation createFormation(Formation formation)  ;

    Formation createFormation(@RequestParam("name") String name,
                              @RequestParam("niveau") String niveau,
                              @RequestParam("desc") String desc,
                              @RequestParam("type") String type,
                              @RequestParam("duree") String duree)  ;

    Formation getFormationById(Long id) throws NotFoundException;

    Formation getFormationByName(String name) throws NotFoundException;

    List<Formation> getAllFormations();

    void deleteFormation(String name);

}
