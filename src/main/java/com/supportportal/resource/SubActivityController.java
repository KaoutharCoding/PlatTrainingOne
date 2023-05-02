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

import java.util.List;
import java.util.Optional;

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

    // GET /subactivities/{id} - Get a subactivity by ID
    @GetMapping("/{id}")
    public SousActivite getSubActivityById(@PathVariable String name) {
        return subactivityService.findSousActivityByName(name);
    }
}

