package com.supportportal.repository;

import com.supportportal.domain.Formation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FormationRepository extends JpaRepository<Formation,Long> {
    void deleteByName(String name);

    Formation findByName(String name);

    boolean existsByname(String name);
}
