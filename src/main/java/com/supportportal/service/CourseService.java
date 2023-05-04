package com.supportportal.service;

import com.supportportal.domain.Course;
import javassist.NotFoundException;

import java.util.List;

public interface CourseService {


    Course createCourse(String name, String niveau, String description, String type, String duree, String etat,
                        String quiz, int ordre, String formationName);

    Course updateCourse(String name, String newName, String niveau, String description, String type, String duree, String etat,
                        String quiz, int ordre, String formationName);

    Course getCourseByName(String name) throws NotFoundException;

    List<Course> findAllCoursesWithFormationName();

    List<Course> getAllCourses();

    String deleteCourse(String courseName) throws NotFoundException;
}

