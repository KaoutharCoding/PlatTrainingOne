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

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

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
                                                     @RequestParam("duree") String duree)  {
        Formation formation = null;
        try {
            formation = new Formation();
            formation.setName(name);
            formation.setNiveau(niveau);
            formation.setDescription(desc);
            formation.setType(type);
            formation.setDuree(duree);

            Formation createdFormation = formationService.createFormation(name,niveau,desc,type,duree);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdFormation);
        } catch (IllegalArgumentException | FormationNameAlreadyExistsException e) {
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }


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
    public ResponseEntity<String> deleteFormation(@PathVariable String name) throws NotFoundException {
        String response = formationService.deleteFormation(name);
        return ResponseEntity.ok("Formation with name " + name + " has been deleted successfully.");
    }

}
