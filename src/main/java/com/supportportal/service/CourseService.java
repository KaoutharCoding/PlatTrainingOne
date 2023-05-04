package com.supportportal.service;

import com.supportportal.domain.Course;
import com.supportportal.domain.CourseRequestDTO;
import com.supportportal.exception.domain.FormationNameAlreadyExistsException;
import javassist.NotFoundException;

import java.util.List;

public interface CourseService {
    Course createCourse(CourseRequestDTO requestDTO) throws FormationNameAlreadyExistsException, NotFoundException;


    Course getCourseByName(String name) throws NotFoundException;

    List<Course> getAllCourses();

    void deleteCourse(String courseName) throws NotFoundException;
}

