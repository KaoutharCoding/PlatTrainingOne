package com.supportportal.resource;

import com.supportportal.domain.*;
import com.supportportal.exception.domain.FormationNameAlreadyExistsException;
import com.supportportal.repository.FormationRepository;
import com.supportportal.service.CourseService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {
    @Autowired
    private CourseService courseService;
    @Autowired
    private FormationRepository formationRepository;

    @PostMapping("/create")
    public ResponseEntity<?> createCourse(@RequestParam("name") String name,
                               @RequestParam("niveau") String niveau,
                               @RequestParam("description") String description,
                               @RequestParam("type") String type,
                               @RequestParam("duree") String duree,
                               @RequestParam("etat") String etat,
                               @RequestParam("quiz") String quiz,
                               @RequestParam("ordre") int ordre,
                               @RequestParam("formationName") String formationName) {
       try{
           Course course = new Course();
           Formation formation = formationRepository.findByName(formationName);
           course.setFormation(formation);
           course = courseService.createCourse(name,niveau,description,type,duree,etat,quiz,ordre,formationName);
           return ResponseEntity.ok(course);
       }catch (IllegalArgumentException e){
           ErrorResponse errorResponse = new ErrorResponse(HttpStatus.FOUND.value(), e.getMessage());
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);

       }

    }

    @PostMapping("/update/{courseName}")
    public ResponseEntity<?> updateCourse(@RequestParam("name") String name,
                                          @RequestParam("newName") String newName,
                                          @RequestParam("niveau") String niveau,
                                          @RequestParam("description") String description,
                                          @RequestParam("type") String type,
                                          @RequestParam("duree") String duree,
                                          @RequestParam("etat") String etat,
                                          @RequestParam("quiz") String quiz,
                                          @RequestParam("ordre") int ordre,
                                          @RequestParam("formationName") String formationName) {
        try{
            Course courseUpdated = courseService.updateCourse(name,newName,niveau,description,type,duree,etat,quiz,ordre,formationName);
            return ResponseEntity.ok(courseUpdated);
        }catch (IllegalArgumentException e){
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.FOUND.value(), e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);

        }

    }

    @GetMapping("/{courseName}")
    public ResponseEntity<Course> getCourseByName(@PathVariable String courseName) {
        try {
            Course course = courseService.getCourseByName(courseName);
            return ResponseEntity.ok(course);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

   /* @GetMapping("/all")
    public ResponseEntity<List<Course>> getAllCourses() {
        List<Course> courses = courseService.getAllCourses();
        return ResponseEntity.ok(courses);
    }

    */
    @GetMapping("/courses-with-formation-name")
    public List<Course> getUsersWithActivityName() {
        return courseService.findAllCoursesWithFormationName();
    }
    @DeleteMapping("/delete/{courseName}")
    @Transactional
    public ResponseEntity<?> deleteCourse(@PathVariable String courseName) throws NotFoundException {
        Course course =courseService.getCourseByName(courseName);
        if (course == null) {
            throw new IllegalArgumentException("Course not found with name: " + courseName);
        }
        courseService.deleteCourse(courseName);
        String message =  "Activity with ID " + courseName + " has been deleted successfully.";
        return ResponseEntity.status(HttpStatus.FOUND).body(message);

        /*try {
            courseService.deleteCourse(name);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }*/
    }
}
