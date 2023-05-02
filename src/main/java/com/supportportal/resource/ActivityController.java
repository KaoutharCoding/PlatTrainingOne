package com.supportportal.resource;

import com.supportportal.domain.Activity;
import com.supportportal.domain.ActivityRequestDTO;
import com.supportportal.domain.ErrorResponse;
import com.supportportal.repository.AcivityRepository;
import com.supportportal.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
        // Check if an activity with the same name already exists
       /* boolean exists = activityService.existsByName(activityName);
        if (exists) {
            String errorMessage = "Activity with name '" + activityName + "' already exists.";
            throw new ResponseStatusException(HttpStatus.CONFLICT, errorMessage);
        }
        Activity createdActivity = activityRepository.save(activity);
        return ResponseEntity.ok(createdActivity);*/
    }
    // Exception handler method to handle ResponseStatusException

    // GET /activities - Get all activities


    @GetMapping
    public List<Activity> getAllActivities() {
        return activityService.getAllActivity();
    }

    // POST /activities - Create a new activity



    // GET /activities/{id} - Get an activity by ID
    @GetMapping("/{id}")
    public Activity getActivityById(@PathVariable String name) throws IOException {
        return activityService.deleteActivity(name);
    }
}

