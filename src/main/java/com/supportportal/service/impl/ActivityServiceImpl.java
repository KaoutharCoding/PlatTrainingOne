package com.supportportal.service.impl;

import com.supportportal.domain.Activity;
import com.supportportal.domain.ActivityRequestDTO;
import com.supportportal.repository.AcivityRepository;
import com.supportportal.service.ActivityService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    AcivityRepository acivityRepository;
    public ActivityServiceImpl(AcivityRepository acivityRepository) {
        this.acivityRepository = acivityRepository;
    }
    @PersistenceContext
    private EntityManager entityManager;
@Override
    public Activity getActivityWithRelatedActivities(String activityName) {
        String query = "SELECT a FROM Activity a LEFT JOIN FETCH a.subActivities WHERE a.name = :activityName";
        TypedQuery<Activity> typedQuery = entityManager.createQuery(query, Activity.class);
        typedQuery.setParameter("activityName", activityName);
        try {
            return typedQuery.getSingleResult();
        } catch (NoResultException e) {
            return null; // Return null if no activity with the given name is found
        }
    }

   /* @Override
    public Activity getActivityWithRelatedActivities(String activityName) {
        return acivityRepository.getActivityWithRelatedActivities(activityName);
    }
    */

    @Override
    public List<Activity> getAllActivity() {
        return acivityRepository.findAll();
    }


    @Override
    public Activity findActivityByName(String name) {
        return acivityRepository.findActivityByName(name);
    }

    public Activity createActivity(Activity activity) {
        String name = activity.getName();
        if(acivityRepository.existsByName(name)) {
            throw new IllegalArgumentException("Activity with name already exists: " + name);
        }
        return acivityRepository.save(activity);
    }


    @Override
    public Activity updateActivity(String name, ActivityRequestDTO requestDTO) throws NotFoundException {
        Activity activity = findActivityByName(name);
        if(!acivityRepository.existsByName(name)) {
            throw new IllegalArgumentException("Activity with this given name doesn't exists , please try again with other name");
        }
        activity.setName(requestDTO.getName());

        return acivityRepository.save(activity);
    }

    @Override
    /**
     * By adding the @Transactional annotation,
     * Spring will automatically manage the
     * transaction for the deleteActivity method.
     */
    @Transactional
    public String deleteActivity(String name) {
        Activity activity = findActivityByName(name);
        acivityRepository.delete(activity);
        return "Activity with name " + name + " has been deleted successfully.";
    }

    @Override
    public Activity getActivityById(Long id) throws NotFoundException {
        return acivityRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Activity not found"));
    }

    public Activity findActivityById(Long activityId) {
        Optional<Activity> optionalActivity = acivityRepository.findById(activityId);
        return optionalActivity.orElse(null);
    }


}
