package com.supportportal.repository;

import com.supportportal.domain.Activite;
import com.supportportal.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AcivityRepository extends JpaRepository<Activite, Long> {

    Activite findUserByName(String name);

}
