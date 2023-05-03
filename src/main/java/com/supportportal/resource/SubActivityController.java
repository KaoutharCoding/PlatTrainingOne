package com.supportportal.resource;

import com.supportportal.domain.Activity;
import com.supportportal.domain.SousActivite;
import com.supportportal.domain.SubactivityRequestDTO;

import com.supportportal.service.ActivityService;
import com.supportportal.service.SousActivityService;
import javassist.NotFoundException;
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
    @GetMapping
    public List<SousActivite> getAllSubActivities() {
        return subactivityService.getAllSousActivity();
    }

    // POST /subactivities - Create a new subactivity
    @PostMapping("/add")
    public ResponseEntity<SousActivite> createSubactivity(@RequestBody SubactivityRequestDTO requestDTO) throws NotFoundException {
        SousActivite subactivity = new SousActivite();
        subactivity.setName(requestDTO.getName());

        Activity activity = activityService.getActivityById(requestDTO.getActivityId());
        subactivity.setActivity(activity);

        SousActivite createdSubactivity = subactivityService.createSubactivity(subactivity);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSubactivity);
    }
    @PutMapping("/update/{name}")
    public ResponseEntity<?> updateSubActivity(@PathVariable String name, @RequestBody SubactivityRequestDTO requestDTO) throws IOException {
        try {
            SousActivite updatedSubActivity = subactivityService.updateSousActivity(name, requestDTO.getActivityId());
            return ResponseEntity.ok(updatedSubActivity);
        } catch (IllegalArgumentException e) {
            String errorMessage = e.getMessage();
            return ResponseEntity.badRequest().body(errorMessage);
        } catch (Exception e) {
            String errorMessage = "Failed to update the subactivity.";
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }

    // GET /subactivities/{id} - Get a subactivity by ID
    @GetMapping("/{name}")
    public SousActivite getSubActivityById(@PathVariable String name) {
        return subactivityService.findSousActivityByName(name);
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

}

