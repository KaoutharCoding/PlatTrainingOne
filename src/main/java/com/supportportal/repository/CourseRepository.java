package com.supportportal.repository;

import com.supportportal.domain.Course;
import com.supportportal.domain.SousActivite;
import javassist.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course,Long> {

    @Query("SELECT c FROM Course c")
    List<Course> findAllCourses();
    @Query("SELECT f.name FROM Formation f WHERE f.id = :formationId")
    String findFormationNameById(@Param("formationId") Long formationId);
    void deleteByName(String name);

    Course findByName(String name);

    boolean existsByName(String name);
}
