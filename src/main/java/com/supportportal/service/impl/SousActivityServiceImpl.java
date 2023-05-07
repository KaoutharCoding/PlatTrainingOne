package com.supportportal.service.impl;

import com.supportportal.domain.Activity;
import com.supportportal.domain.SousActivite;
import com.supportportal.repository.SousAcivityRepository;
import com.supportportal.service.ActivityService;
import com.supportportal.service.SousActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.io.IOException;
import java.util.List;

@Service
public class SousActivityServiceImpl implements SousActivityService {

    @Autowired
    SousAcivityRepository sousAcivityRepository;

    @Autowired
    private ActivityService activityService;

    public SousActivityServiceImpl(SousAcivityRepository sousAcivityRepository, ActivityService activityService) {
        this.sousAcivityRepository = sousAcivityRepository;
        this.activityService = activityService;
    }

      //  @PersistenceContext
        //private EntityManager entityManager;



    @Override
        public SousActivite getSubactivityWithActivities(String subactivityName) {
            String query = "SELECT s FROM SousActivite s LEFT JOIN FETCH s.activity WHERE s.name = :subactivityName";
            TypedQuery<SousActivite> typedQuery = entityManager.createQuery(query, SousActivite.class);
            typedQuery.setParameter("subactivityName", subactivityName);
            try {
                return typedQuery.getSingleResult();
            } catch (NoResultException e) {
                return null; // Return null if no subactivity with the given name is found
            }
        }

    @Override
    public SousActivite getSubactivityWithActivities() {
        return null;
    }

    @Override
    public List<SousActivite> getAllSousActivity() {
        return sousAcivityRepository.findAll();
    }

    @Override
    public SousActivite findSousActivityByName(String name) {
        return sousAcivityRepository.findSousActivityByName(name);
    }


    @Override
    public SousActivite updateSubactivity(String subactivityName, String newName) {
        SousActivite subactivity = sousAcivityRepository.findSousActivityByName(subactivityName);

        if (subactivity == null) {
            throw new IllegalArgumentException("Subactivity not found with name: " + subactivityName);
        }

        subactivity.setName(newName);
        // Update other properties of subactivity if needed

        return sousAcivityRepository.save(subactivity);
    }



    @Override
    public void deleteSubActivity(String name) throws IOException {
        SousActivite subActivite = findSousActivityByName(name);
        sousAcivityRepository.delete(subActivite);
    }

    @Override
    public void deleteSubActivityAll() throws IOException {
        sousAcivityRepository.deleteAll();
    }
@Override
    public SousActivite createSubactivity(SousActivite requestDTO) {
        String activityName = requestDTO.getActivityName();
        String subactivityName = requestDTO.getName();

        Activity activity = activityService.findActivityByName(activityName);

        if (activity == null) {
            throw new IllegalArgumentException("Activity not found with name: " + activityName);
        }
        // Check if a subactivity with the same name already exists
        if (sousAcivityRepository.existsByName(subactivityName)) {
            throw new IllegalArgumentException("Subactivity with name '" + subactivityName + "' already exists.");
        }

        SousActivite subactivity = new SousActivite();
        subactivity.setName(subactivityName);
        subactivity.setActivity(activity);
        // Set other properties of the subactivity if needed
        subactivity.setActivityName(activityName);
        return sousAcivityRepository.save(subactivity);
    }



    @Override
    public List<SousActivite> findAllUsersWithActivityName() {
        List<SousActivite> sousActivite = getAllSousActivity();
        for (SousActivite sub : sousActivite) {
            if (sub.getActivity() != null) {
                String activityName = findActivityNameById(sub.getActivity().getId());
                sub.setActivityName(activityName);
            }
        }
        return sousActivite;
    }

    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public String findActivityNameById(Long activityId) {
        String query = "SELECT a.name FROM Activity a WHERE a.id = :activityId";
        TypedQuery<String> typedQuery = entityManager.createQuery(query, String.class);
        typedQuery.setParameter("activityId", activityId);
        try {
            return typedQuery.getSingleResult();
        } catch (NoResultException e) {
            return null; // Return null if no activity with the given ID is found
        }
    }

    @Override
    public List<SousActivite> findAllActivitiesWithActivityName() {
        List<SousActivite> sousActivites = getAllSousActivity();
        for (SousActivite sub : sousActivites) {
            if (sub.getActivity() != null) {
                String activityName = findActivityNameById(sub.getActivity().getId());
                sub.setActivityName(activityName);
            }
        }
        return sousActivites;
    }

}
