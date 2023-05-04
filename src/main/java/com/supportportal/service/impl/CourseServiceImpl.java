package com.supportportal.service.impl;

import com.supportportal.domain.Course;
import com.supportportal.domain.CourseRequestDTO;
import com.supportportal.domain.Formation;
import com.supportportal.exception.domain.FormationNameAlreadyExistsException;
import com.supportportal.repository.CourseRepository;
import com.supportportal.repository.FormationRepository;
import com.supportportal.service.CourseService;
import com.supportportal.service.FormationService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private FormationService formationService;
  //  private FormationRepository formationRepository;

    public CourseServiceImpl(CourseRepository courseRepository, FormationService formationService) {
        this.courseRepository = courseRepository;
        this.formationService = formationService;
    }

    @Override
    public Course createCourse(CourseRequestDTO requestDTO) throws FormationNameAlreadyExistsException, NotFoundException {
        String formationName = requestDTO.getFormationName();

        Formation formation = formationService.getFormationByName(formationName);

            Course course = new Course();
            course.setCourseName(requestDTO.getName());
            course.setNiveau(requestDTO.getNiveau());
            course.setDescription(requestDTO.getDesc());
            course.setType(requestDTO.getType());
            course.setDuree(requestDTO.getDuree());
            course.setEtat(requestDTO.getEtat());
            course.setQuiz(requestDTO.getQuiz());
            course.setOrdre(requestDTO.getOrdre());
            course.setFormation(formation);
            return courseRepository.save(course);
        }


        //    throw new FormationNameAlreadyExistsException("Formation with name '" + formationName + "' doesn't exists.");



        // Create the course and associate it with the formation




    @Override
    public Course getCourseByName(String name) throws NotFoundException {
        return courseRepository.findByName(name);
    }


    @Override
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @Override
    public void deleteCourse(String courseName) throws NotFoundException {
        courseRepository.deleteByName(courseName);

    }

}
