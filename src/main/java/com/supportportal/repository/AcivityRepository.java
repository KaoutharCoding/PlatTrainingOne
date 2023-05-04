package com.supportportal.repository;

import com.supportportal.domain.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

//@Repository
public interface AcivityRepository extends JpaRepository<Activity, Long> {

    @PersistenceContext
    EntityManager entityManager = null;

    public default Activity getActivityWithRelatedActivities(String activityName) {
        String query = "SELECT a FROM Activity a LEFT JOIN FETCH a.subactivite WHERE a.name = :activityName";
        TypedQuery<Activity> typedQuery = entityManager.createQuery(query, Activity.class);
        typedQuery.setParameter("activityName", activityName);
        try {
            return typedQuery.getSingleResult();
        } catch (NoResultException e) {
            return null; // Return null if no activity with the given name is found
        }
    }


    Activity findActivityByName(String name);

    void deleteByName(String name);

    boolean existsByName(String activityName);

}
