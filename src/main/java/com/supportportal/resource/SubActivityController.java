package com.supportportal.resource;

import com.supportportal.domain.SousActivite;
import com.supportportal.domain.SubactivityRequestDTO;

import com.supportportal.service.ActivityService;
import com.supportportal.service.SousActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/subactivities")
public class SubActivityController {
    @Autowired
    private SousActivityService subactivityService;

    @Autowired
    private ActivityService activityService;

    // GET /subactivities - Get all subactivities
    @GetMapping("/list")
    public List<SousActivite> getAllSubActivities() {
        return subactivityService.getAllSousActivity();
    }

    @GetMapping("/subactivitues-with-activity-name")
    public List<SousActivite> getUsersWithActivityName() {
        return subactivityService.findAllActivitiesWithActivityName();
    }
    // POST /subactivities - Create a new subactivity
    @PutMapping("/update")
    public ResponseEntity<?> updateSubactivity(
            @RequestParam String name,
            @RequestParam String newName
    ) {
        try {
            SousActivite updatedSubactivity = subactivityService.updateSubactivity(name, newName);
            return ResponseEntity.ok(updatedSubactivity);
        } catch (IllegalArgumentException e) {
            String errorMessage = e.getMessage();
            return ResponseEntity.badRequest().body(errorMessage);
        } catch (Exception e) {
            String errorMessage = "Failed to update the subactivity.";
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }

    // GET /subactivities/{id} - Get a subactivity by ID
   /* @GetMapping("/{name}")
    public SousActivite getSubActivityById(@PathVariable String name) {
        return subactivityService.findSousActivityByName(name);
    }
*/
    @GetMapping("/all")
    public SousActivite getSubactivityWithActivities() {
        return subactivityService.getSubactivityWithActivities();
    }

    @GetMapping("/")
    public List<SousActivite>getSubActivity() {
        return  subactivityService.getAllSousActivity();
    }


    @DeleteMapping("/delete/{name}")
    public ResponseEntity<?> deleteSubActivity(@PathVariable String name) throws IOException {
        SousActivite subActivity = subactivityService.findSousActivityByName(name);

        if (subActivity == null) {
            String message = "SubActivity with name " + name + " does not exist.";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        // Perform the deletion of subactivity here
        subactivityService.deleteSubActivity(name);
        String message = "SubActivity with name " + name + " has been deleted successfully.";
        return ResponseEntity.ok(message);
    }
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteSubActivityAll() throws IOException {

        // Perform the deletion of subactivity here
        subactivityService.deleteSubActivityAll();


        String message = "all SubActivity  has been deleted successfully.";
        return ResponseEntity.ok(message);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createSubactivity(
            @RequestParam("activityName") String activityName,
            @RequestParam("subactivityName") String subactivityName) {
        try {
            SousActivite requestDTO = new SousActivite();
            requestDTO.setActivityName(activityName);
            requestDTO.setName(subactivityName);

            SousActivite createdSubactivity = subactivityService.createSubactivity(requestDTO);
            return ResponseEntity.ok(createdSubactivity);
        } catch (IllegalArgumentException e) {
            String errorMessage = e.getMessage();
            return ResponseEntity.badRequest().body(errorMessage);
        } catch (Exception e) {
            String errorMessage = "Failed to create the subactivity.";
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }


}

