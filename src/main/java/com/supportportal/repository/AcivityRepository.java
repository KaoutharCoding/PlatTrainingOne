package com.supportportal.repository;

import com.supportportal.domain.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AcivityRepository extends JpaRepository<Activity, Long> {

    Activity findActivityByName(String name);

    void deleteByName(String name);

    boolean existsByName(String activityName);
}
