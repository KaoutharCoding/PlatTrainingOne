package com.supportportal.service.impl;

import com.supportportal.domain.Course;
import com.supportportal.domain.Formation;
import com.supportportal.domain.User;
import com.supportportal.exception.domain.NotAnImageFileException;
import com.supportportal.repository.CourseRepository;
import com.supportportal.repository.FormationRepository;
import com.supportportal.service.CourseService;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static com.supportportal.constant.FileConstant.*;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static org.springframework.http.MediaType.*;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private FormationRepository formationRepository;

    private Logger LOGGER = LoggerFactory.getLogger(getClass());


    public CourseServiceImpl(CourseRepository courseRepository, FormationRepository formationRepository) {
        this.courseRepository = courseRepository;
        this.formationRepository = formationRepository;
    }



    public void removeEntity(Course entity) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.remove(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }


    @Override
    public Course updateCourse(String name, String newName, String niveau, String description, String type, String duree, String etat,
                               String quiz, int ordre, String formationName) {
        if (!courseRepository.existsByName(name)) {
            throw new IllegalArgumentException("Course with name '" + name + "' doesn't exists.");
        }

        Formation formation = formationRepository.findByName(formationName);
        if (formation == null) {
            throw new IllegalArgumentException("Formation with name '" + formationName + "' not found.");
        }

        Course course = courseRepository.findByName(name);
        course.setCourseName(newName);
        course.setNiveau(niveau);
        course.setDescription(description);
        course.setType(type);
        course.setDuree(duree);
        course.setEtat(etat);
        course.setQuiz(quiz);
        course.setOrdre(ordre);
        course.setFileData(course.getFileData());
        course.setType(type);
        course.setFormation(formation);

        return courseRepository.save(course);
    }

    //    throw new FormationNameAlreadyExistsException("Formation with name '" + formationName + "' doesn't exists.");


    // Create the course and associate it with the formation


    @Override
    public Course getCourseByName(String name) throws NotFoundException {
        Course course = courseRepository.findByName(name);
        if (course == null) {
            String m = "not found course by this name";
            throw new NotFoundException(m);
        }
        return course;
    }


@Override
public List<Course> findAllCoursesWithFormationName() {
        List<Course> courses = getAllCourses();
        for (Course course : courses) {
            if (course.getFormation() != null) {
                String formationName = findFormationNameById(course.getFormation().getId());
                course.setFormationName(formationName);
            }
        }
        return courses;
    }
    @PersistenceContext
    private EntityManager entityManager;
    private String findFormationNameById(Long formationId) {
        String query = "SELECT f.name FROM Formation f WHERE f.id = :formationId";
        TypedQuery<String> typedQuery = entityManager.createQuery(query, String.class);
        typedQuery.setParameter("formationId", formationId);
        try {
            return typedQuery.getSingleResult();
        } catch (NoResultException e) {
            return null; // Return null if no activity with the given ID is found
        }
    }


    @Override
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @Override
    public String deleteCourse(String courseName) throws NotFoundException {
        Course course = courseRepository.findByName(courseName);
        courseRepository.deleteByName(courseName);
        return "Activity with name " + courseName + " has been deleted successfully.";

    }
   // @Value("${file.upload-dir}") // Specify the file upload directory
    //private String uploadDir;
    @Override
    public Course createCourse(String name, String niveau, String description, String type, String duree, String etat,
                               String quiz, int ordre, String formationName, MultipartFile file) throws IOException, NotAnImageFileException {
        if (courseRepository.existsByName(name)) {
            throw new IllegalArgumentException("Course with name '" + name + "' already exists.");
        }

        Formation formation = formationRepository.findByName(formationName);
        if (formation == null) {
            throw new IllegalArgumentException("Formation with name '" + formationName + "' not found.");
        }

        // Save the file to the upload directory
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
      //  Path filePath = Paths.get(uploadDir, fileName);
        //Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        // Get the file bytes
        byte[] fileBytes;
        try {
            fileBytes = file.getBytes();
        } catch (IOException e) {
            throw new RuntimeException("Failed to read file data", e);
        }
        // Create the course object
      //  Course course = new Course();


        Course course = new Course(name, niveau, description, type, duree, etat, quiz, ordre, file ,formation);
       String fileNameSub  = file.getOriginalFilename().substring(0,file.getOriginalFilename().lastIndexOf('.'));
        course.setFileType(file.getContentType());
        course.setFileData(file.getBytes());
        course.setOriginalFilename(fileNameSub);
        course.setFormationName(formationName);
        course.setFormation(formation);


        return  course;

    }

    private void saveProfileImage(Course user, MultipartFile profileImage) throws IOException, NotAnImageFileException {
        if (profileImage != null) {
            if(!Arrays.asList(IMAGE_JPEG_VALUE, IMAGE_PNG_VALUE, IMAGE_GIF_VALUE).contains(profileImage.getContentType())) {
                throw new NotAnImageFileException(profileImage.getOriginalFilename() + NOT_AN_IMAGE_FILE);
            }
            Path userFolder = Paths.get(USER_FOLDER + user.getCourseName()).toAbsolutePath().normalize();
            if(!Files.exists(userFolder)) {
                Files.createDirectories(userFolder);
                LOGGER.info(DIRECTORY_CREATED + userFolder);
            }
            Files.deleteIfExists(Paths.get(userFolder + user.getCourseName() + DOT + JPG_EXTENSION));
            Files.copy(profileImage.getInputStream(), userFolder.resolve(user.getCourseName() + DOT + JPG_EXTENSION), REPLACE_EXISTING);
            user.setFileUrl(setProfileImageUrl(user.getCourseName()));
            courseRepository.save(user);
            LOGGER.info(FILE_SAVED_IN_FILE_SYSTEM + profileImage.getOriginalFilename());
        }
    }

    private String getTemporaryProfileImageUrl(String username) {
        return ServletUriComponentsBuilder.fromCurrentContextPath().path(DEFAULT_USER_IMAGE_PATH + username).toUriString();
    }

    private String setProfileImageUrl(String username) {
        return ServletUriComponentsBuilder.fromCurrentContextPath().path(USER_IMAGE_PATH + username + FORWARD_SLASH
                + username + DOT + JPG_EXTENSION).toUriString();
    }


    public static URL createUrlFromFile(String filePath) throws MalformedURLException, MalformedURLException {
        File file = new File(filePath);
        return file.toURI().toURL();
    }


    }

