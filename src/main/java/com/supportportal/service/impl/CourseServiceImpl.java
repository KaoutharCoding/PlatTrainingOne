package com.supportportal.service.impl;

import com.supportportal.domain.Course;
import com.supportportal.domain.Formation;
import com.supportportal.exception.domain.NotAnImageFileException;
import com.supportportal.repository.CourseRepository;
import com.supportportal.repository.FormationRepository;
import com.supportportal.service.CourseService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.UUID;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private FormationRepository formationRepository;

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
      //  Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        // Get the file bytes
        byte[] fileBytes;
        try {
            fileBytes = file.getBytes();
        } catch (IOException e) {
            throw new RuntimeException("Failed to read file data", e);
        }
        // Create the course object
        Course course = new Course();


        course.setCourseName(name);
        course.setNiveau(niveau);
        course.setDescription(description);
        course.setType(type);
        course.setDuree(duree);
        course.setEtat(etat);
        course.setQuiz(quiz);
        course.setOrdre(ordre);
        course.setFormation(formation);
        course.setFormationName(formationName);

        course.setFileData(fileBytes);
        course.setFileType(file.getContentType());
        course.setOriginalFilename(file.getOriginalFilename().substring(0,file.getOriginalFilename().lastIndexOf('.')));
        saveFileAndGetUrl(file);
        String serverUrl = "http://localhost:8082/courses";
        createFileUrl(fileName,serverUrl);
        String newUrl = createFileUrl(fileName, serverUrl);
        URL fileUrl = createUrlFromFile(newUrl);
      //  course.setFileUrl(fileUrl);

        // Set the file URL in a separate variable
       /* String fileUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/files/")
                .path(fileName)
                .toUriString();
*/
        // Save the course in the database
        //  Course savedCourse = courseRepository.save(course);

        // Return the saved course and the file URL
        //   savedCourse.setFileUrl(fileUrl);
        courseRepository.save(course);


        return  course;

    }

    public static URL createUrlFromFile(String filePath) throws MalformedURLException, MalformedURLException {
        File file = new File(filePath);
        return file.toURI().toURL();
    }

    @Override
    public String createFileUrl(String fileName, String serverUrl) {
        return serverUrl + "/files/" + fileName;
    }

    public String saveFileAndGetUrl(MultipartFile file) throws IOException {
        // Save the file to a local file system or a cloud-based storage service
        // Here's an example of saving to a local file system
        String fileName = UUID.randomUUID().toString() + "." + file.getOriginalFilename()/*.split("\\.")[1]*/;
        String filePath = "/C:/Users//kababi//Downloads//courses/" + fileName;
        File dest = new File(filePath);
        file.transferTo(dest);

        // Generate the file URL
        String fileUrl = "http://localhost:8082/courses/files/" + fileName;

        return fileUrl;
    }

    @Override
    public void openURL(String url) {
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            try {
                Desktop.getDesktop().browse(new URI(url));
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        }


    }}
