package com.supportportal.repository;

import com.supportportal.domain.Course;
import com.supportportal.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {

    Course findCourseByTitle(String title);

    void deleteByTitle(String title);
}
