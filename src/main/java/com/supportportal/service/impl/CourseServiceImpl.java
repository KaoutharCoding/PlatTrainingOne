package com.supportportal.service.impl;

import com.supportportal.domain.Course;
import com.supportportal.domain.Formation;
import com.supportportal.repository.CourseRepository;
import com.supportportal.repository.FormationRepository;
import com.supportportal.service.CourseService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import java.util.List;

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
    public Course createCourse(String name, String niveau, String description, String type, String duree, String etat,
                               String quiz, int ordre, String formationName) {
        if (courseRepository.existsByName(name)) {
            throw new IllegalArgumentException("Course with name '" + name + "' already exists.");
        }

        Formation formation = formationRepository.findByName(formationName);
        if (formation == null) {
            throw new IllegalArgumentException("Formation with name '" + formationName + "' not found.");
        }

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

        return courseRepository.save(course);
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

}
