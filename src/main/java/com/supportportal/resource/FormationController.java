package com.supportportal.resource;


import com.supportportal.domain.ErrorResponse;
import com.supportportal.domain.Formation;
import com.supportportal.exception.ExceptionHandling;
import com.supportportal.exception.domain.FormationNameAlreadyExistsException;
import com.supportportal.service.FormationService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/formations")
public class FormationController extends ExceptionHandling {

    @Autowired
    private FormationService formationService;


    @PostMapping("/create")
    public ResponseEntity<?> createFormation(@RequestParam("name") String name,
                                                     @RequestParam("niveau") String niveau,
                                                     @RequestParam("desc") String desc,
                                                     @RequestParam("type") String type,
                                                     @RequestParam("duree") String duree,
                                                   @RequestParam("subActivityName")String subActivityName)  {
        try {
            Formation formation = new Formation();
            Formation createdFormation = formationService.createFormation(name,niveau,desc,type,duree,subActivityName);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdFormation);

        } catch (IllegalArgumentException e) {
            ErrorResponse errorResponse = new ErrorResponse(BAD_REQUEST.value(), e.getMessage());
            return ResponseEntity.status(BAD_REQUEST).body(errorResponse);
        }
    }

    @GetMapping("/with-subactivity-and-activity-names") public List<Formation> getFormationsWithSubActivityAndActivityNames() {
        return formationService.findAllFormationsWithSubActivityAndActivityNames(); }

    @GetMapping("/{name}")
    public ResponseEntity<?> getFormationByName(@PathVariable String name) throws NotFoundException {
        Formation formation = formationService.getFormationByName(name);
        if(formation == null){
            return new ResponseEntity(NOT_FOUND);
        }
        return new ResponseEntity<>(formation, OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Formation>> getAllFormations() {
        List<Formation> formations = formationService.getAllFormations();
        return ResponseEntity.ok(formations);
    }

    @DeleteMapping("/delete/{name}")
    public ResponseEntity<?> deleteFormation(@PathVariable String name) throws NotFoundException {
        Formation formation = formationService.getFormationByName(name);
        if (formation == null) {
            throw new IllegalArgumentException("Activity not found with name: " + name);
        }
        formationService.deleteFormation(name);
        String message =  "Formation with name " + name + " has been deleted successfully.";
        return ResponseEntity.status(FOUND).body(message);    }


    @PutMapping("/update")
    public ResponseEntity<Formation> updateFormation(
            @RequestParam String currentFormationName,
            @RequestParam String newName,
            @RequestParam String newNiveau,
            @RequestParam String newDescription,
            @RequestParam String newType,
            @RequestParam String newDuree,
            @RequestParam String newSousActiviteName) {

            Formation updatedFormation = formationService.updateFormation(currentFormationName,newName,newNiveau,newDescription,newType,newDuree,newSousActiviteName );
            return new ResponseEntity<>(updatedFormation, OK);


    }



}
