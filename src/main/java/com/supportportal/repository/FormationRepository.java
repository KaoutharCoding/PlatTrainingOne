package com.supportportal.repository;

import com.supportportal.domain.Formation;
import com.supportportal.domain.SousActivite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FormationRepository extends JpaRepository<Formation,Long> {

    @Query("SELECT f FROM Formation f")
    List<Formation> findAllFormation();

    @Query("SELECT s.name FROM SousActivite s WHERE s.id = :sousActivityId")
    String findActivityNameById(@Param("sousActivityId") Long sousActivityId);

    default List<Formation> findAllActivitiesWithActivityName() {
        List<Formation> formations = findAllFormation();
        for (Formation formation : formations) {
            if (formation.getSousActivite() != null) {
                String sousActivityName = findActivityNameById(formation.getSousActivite().getId());
                formation.setSubActivityName(sousActivityName);
            }
        }
        return formations;
    }
    void deleteByName(String name);

    Formation findByName(String name);

    boolean existsByname(String name);
}
