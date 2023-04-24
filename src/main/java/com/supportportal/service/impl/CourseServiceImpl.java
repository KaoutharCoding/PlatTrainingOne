package com.supportportal.service.impl;

import com.supportportal.domain.Course;
import com.supportportal.enumeration.Etat;
import com.supportportal.enumeration.Languages;
import com.supportportal.enumeration.Level;
import com.supportportal.enumeration.Type;
import com.supportportal.exception.domain.*;
import com.supportportal.repository.CourseRepository;
import com.supportportal.service.CourseService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static com.supportportal.constant.CourseImplConstant.*;
import static com.supportportal.constant.FileConstant.*;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.springframework.http.MediaType.*;

@Service
@Transactional
public class CourseServiceImpl implements CourseService {
    private Logger LOGGER = LoggerFactory.getLogger(getClass());
    private CourseRepository courseRepository;


    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository ) {
        this.courseRepository = courseRepository;

    }


    @Override
    public List<Course> getCourses() {
        return courseRepository.findAll();
    }

    public Course findCourseByTitle(String title){
        return courseRepository.findCourseByTitle(title);
    }


    @Override
    public Course addNewCourse(String title, String description, Type type, String duration, Level level, Languages language, String category, MultipartFile profileImage) throws IOException, NotAnImageFileException, UserNotFoundException, UsernameExistException, EmailExistException {
        validateNewCourse(EMPTY, title);
        Course course = new Course();
        course.setCourseId(generateCourseId());
        course.setTitle(title);
        course.setDescription(description);
        course.setType(type);
        course.setDuration(duration);
        course.setLanguage(language);
        course.setLevel(level);
        //course.setEtat(state);
        course.setCategory(category);
       course.setProfileImageUrl(getTemporaryProfileImageUrl(title));
       // course.setProfileImageUrl(title);
        courseRepository.save(course);
        saveProfileImage(course, profileImage);
        return course;
    }

    @Override
    public Course updateCourse(String currentTitle, String newTitle, String newDescription, Type newType, String newDuration, Level newLevel, Languages newLanguage, String category, MultipartFile profileImage) throws UserNotFoundException, UsernameExistException, IOException, NotAnImageFileException, EmailExistException {
        Course currentCourse = validateNewCourse(currentTitle, newTitle);
        currentCourse.setDescription(newDescription);
        currentCourse.setType(newType);
        currentCourse.setDuration(newDuration);
        currentCourse.setLanguage(newLanguage);
        currentCourse.setLevel(newLevel);
      //  currentCourse.setEtat(state);
        currentCourse.setCategory(category);
        courseRepository.save(currentCourse);
        saveProfileImage(currentCourse,profileImage);
        return currentCourse;    }


    @Override
    public Course updateProfileImage(String title, MultipartFile profileImage) throws CourseNotFoundException, TitleExistException, IOException, NotAnImageFileException, UserNotFoundException, EmailExistException, UsernameExistException {
        Course course = validateNewCourse(title, null);
        saveProfileImage(course, profileImage);
        return course;
    }

    @Override
    public void deleteCourse(String title) throws IOException {
        Course course = courseRepository.findCourseByTitle(title);
        Path userFolder = Paths.get(USER_FOLDER + course.getTitle()).toAbsolutePath().normalize();
        FileUtils.deleteDirectory(new File(userFolder.toString()));
        courseRepository.deleteById(course.getId());
    }




    private void saveProfileImage(Course course, MultipartFile profileImage) throws IOException, NotAnImageFileException {
        if (profileImage != null) {
            if(!Arrays.asList(IMAGE_JPEG_VALUE, IMAGE_PNG_VALUE, IMAGE_GIF_VALUE).contains(profileImage.getContentType())) {
                throw new NotAnImageFileException(profileImage.getOriginalFilename() + NOT_AN_IMAGE_FILE);
            }
            Path userFolder = Paths.get(USER_FOLDER + course.getTitle()).toAbsolutePath().normalize();
            if(!Files.exists(userFolder)) {
                Files.createDirectories(userFolder);
                LOGGER.info(DIRECTORY_CREATED + userFolder);
            }
            Files.deleteIfExists(Paths.get(userFolder + course.getTitle() + DOT + JPG_EXTENSION));
            Files.copy(profileImage.getInputStream(), userFolder.resolve(course.getTitle() + DOT + JPG_EXTENSION), REPLACE_EXISTING);
            course.setProfileImageUrl(setProfileImageUrl(course.getTitle()));
            courseRepository.save(course);
            LOGGER.info(FILE_SAVED_IN_FILE_SYSTEM + profileImage.getOriginalFilename());
        }
    }

    private String setProfileImageUrl(String title) {
        return ServletUriComponentsBuilder.fromCurrentContextPath().path(USER_IMAGE_PATH + title + FORWARD_SLASH
        + title + DOT + JPG_EXTENSION).toUriString();
    }



    private String getTemporaryProfileImageUrl(String title) {
        return ServletUriComponentsBuilder.fromCurrentContextPath().path(DEFAULT_USER_IMAGE_PATH + title).toUriString();
    }



    private String generateCourseId() {
        return RandomStringUtils.randomAlphanumeric(10);
    }



    private Course validateNewCourse(String currentTitle, String newTitle) throws UserNotFoundException, UsernameExistException, EmailExistException {
        Course courseByNewTitle = findCourseByTitle(newTitle);
        if(StringUtils.isNotBlank(currentTitle)) {
            Course currentUser = findCourseByTitle(currentTitle);
            if(currentUser == null) {
                throw new UserNotFoundException(NO_COURSE_FOUND_BY_TITLE + currentTitle + '!');
            }
            if(courseByNewTitle != null && !currentUser.getId().equals(courseByNewTitle.getId())) {
                throw new UsernameExistException(TITLE_ALREADY_EXISTS + '!');
            }

            return currentUser;
        } else {
            if(courseByNewTitle != null) {
                throw new UsernameExistException(TITLE_ALREADY_EXISTS);
            }

            return null;
        }
    }

}
