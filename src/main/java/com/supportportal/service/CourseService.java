package com.supportportal.service;

import com.supportportal.domain.Course;
import com.supportportal.exception.domain.NotAnImageFileException;
import javassist.NotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CourseService {



    Course createCourse(String name, String niveau, String description, String type, String duree, String etat,
                        String quiz, int ordre, String formationName, MultipartFile file) throws IOException, NotAnImageFileException;

    Course updateCourse(String name, String newName, String niveau, String description, String type, String duree, String etat,
                        String quiz, int ordre, String formationName);

    Course getCourseByName(String name) throws NotFoundException;

    List<Course> findAllCoursesWithFormationName();

    List<Course> getAllCourses();

    String deleteCourse(String courseName) throws NotFoundException;

    String createFileUrl(String fileName, String serverUrl);

    void openURL(String url);
}

