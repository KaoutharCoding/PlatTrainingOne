package com.supportportal.repository;

import com.supportportal.domain.Activity;
import com.supportportal.domain.SousActivite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AcivityRepository extends JpaRepository<Activity, Long> {

    Activity findActivityByName(String name);

    void deleteByName(String name);

    boolean existsByName(String activityName);

}
