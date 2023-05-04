package com.supportportal.repository;

import com.supportportal.domain.Activity;
import com.supportportal.domain.SousActivite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SousAcivityRepository extends JpaRepository<SousActivite, Long> {

    @Query("SELECT s FROM SousActivite s")
    List<SousActivite> findAllSousActivite();

    @Query("SELECT a.name FROM Activity a WHERE a.id = :activityId")
    String findActivityNameById(@Param("activityId") Long activityId);

    default List<SousActivite> findAllActivitiesWithActivityName() {
        List<SousActivite> sousActivites = findAllSousActivite();
        for (SousActivite sousActivite : sousActivites) {
            if (sousActivite.getActivity() != null) {
                String activityName = findActivityNameById(sousActivite.getActivity().getId());
                sousActivite.setActivityName(activityName);
            }
        }
        return sousActivites;
    }
    SousActivite findSousActivityByName(String name);

    boolean existsByName(String activityName);

    SousActivite findByActivityAndName(Activity activity, String subactivityName);

    SousActivite findByName(String subactivityName);
}
