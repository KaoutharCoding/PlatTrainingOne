package com.supportportal.repository;

import com.supportportal.domain.Course;
import javassist.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course,Long> {

    void deleteByName(String name);

    Course findByName(String name);
}
