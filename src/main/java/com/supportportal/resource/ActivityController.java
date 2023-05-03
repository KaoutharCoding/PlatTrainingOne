package com.supportportal.resource;

import com.supportportal.domain.Activity;
import com.supportportal.domain.ActivityRequestDTO;
import com.supportportal.domain.ErrorResponse;
import com.supportportal.service.ActivityService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/activities")
public class ActivityController {
    @Autowired
    private ActivityService activityService;


    @PostMapping("/add")
        public ResponseEntity<?> createActivity(@RequestBody ActivityRequestDTO requestDTO) {
        try {
            Activity activity = new Activity();
            activity.setName(requestDTO.getName());
            Activity createdActivity = activityService.createActivity(activity);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdActivity);
        } catch (IllegalArgumentException e) {
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

        @PutMapping("/update/{name}")
        public ResponseEntity<Activity> updateActivity(@PathVariable String name, @RequestBody ActivityRequestDTO requestDTO) throws NotFoundException {
            Activity updatedActivity = activityService.updateActivity(name, requestDTO);
            return ResponseEntity.ok(updatedActivity);
        }


        // Check if an activity with the same name already exists
       /* boolean exists = activityService.existsByName(activityName);
        if (exists) {
            String errorMessage = "Activity with name '" + activityName + "' already exists.";
            throw new ResponseStatusException(HttpStatus.CONFLICT, errorMessage);
        }
        Activity createdActivity = activityRepository.save(activity);
        return ResponseEntity.ok(createdActivity);*/

    // Exception handler method to handle ResponseStatusException

    // GET /activities - Get all activities


    @GetMapping
    public List<Activity> getAllActivities() {
        return activityService.getAllActivity();
    }

    // POST /activities - Create a new activity



    // GET /activities/{id} - Get an activity by ID
    @GetMapping("/{name}")
    public ResponseEntity<Activity> getActivityByName(@PathVariable String name) throws IOException {
        Activity activity = activityService.findActivityByName(name);
        return ResponseEntity.ok(activity);

    }

    @DeleteMapping("/delete/{name}")
    public ResponseEntity<?> deleteActivity(@PathVariable String name) throws IOException {
        Activity activity = activityService.findActivityByName(name);

        if (activity == null) {
            throw new IllegalArgumentException("Activity not found with name: " + name);
        }

        activityService.deleteActivity(name);
        String message =  "Activity with ID " + name + " has been deleted successfully.";
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);

    }
    }

