package com.supportportal.service;

import com.supportportal.domain.Course;
import com.supportportal.enumeration.Etat;
import com.supportportal.enumeration.Languages;
import com.supportportal.enumeration.Level;
import com.supportportal.enumeration.Type;
import com.supportportal.exception.domain.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CourseService {


    List<Course> getCourses();

    Course findCourseByTitle(String title);

    Course addNewCourse(String title, String description, Type type, String duration , Level level, Languages language ,  String categorie, MultipartFile profileImage) throws IOException, NotAnImageFileException, UserNotFoundException, UsernameExistException, EmailExistException;


    void deleteCourse(String title) throws IOException;


    Course updateCourse(String currentTitle, String newTitle, String newDescription, Type newType, String newDuration, Level newLevel, Languages newLanguage, String newCategorie,MultipartFile profileImage) throws UserNotFoundException, UsernameExistException, IOException, NotAnImageFileException, EmailExistException;


    Course updateProfileImage(String title , MultipartFile profileImage) throws TitleExistException, IOException, NotAnImageFileException, CourseNotFoundException, UserNotFoundException, EmailExistException, UsernameExistException;
}
