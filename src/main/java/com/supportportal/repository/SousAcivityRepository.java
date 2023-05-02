package com.supportportal.repository;

import com.supportportal.domain.SousActivite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SousAcivityRepository extends JpaRepository<SousActivite, Long> {

    SousActivite findSousActivityByName(String name);

    boolean existsByName(String activityName);
}
