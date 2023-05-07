package com.supportportal.resource;

import com.supportportal.domain.*;
import com.supportportal.exception.domain.NotAnImageFileException;
import com.supportportal.repository.FormationRepository;
import com.supportportal.service.CourseService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/courses")
public class CourseController {
    @Autowired
    private CourseService courseService;
    @Autowired
    private FormationRepository formationRepository;

    @PostMapping("/upload")
    public ResponseEntity<Course> uploadCourseFile(@RequestParam("file")  MultipartFile file,
                                                   @RequestParam("name") String name,
                                                   @RequestParam("niveau") String niveau,
                                                   @RequestParam("description") String description,
                                                   @RequestParam("type") String type,
                                                   @RequestParam("duree") String duree,
                                                   @RequestParam("etat") String etat,
                                                   @RequestParam("quiz") String quiz,
                                                   @RequestParam("ordre") int ordre,
                                                   @RequestParam("formationName") String formationName) throws IOException, NotAnImageFileException {

        Course course = courseService.createCourse(name, niveau, description, type, duree, etat, quiz, ordre, formationName,file);
        return new ResponseEntity<>(course, HttpStatus.CREATED);
    }
    @GetMapping("/courses/{courseName}")
    public ResponseEntity<byte[]> getCourseFile(@PathVariable String courseName) throws NotFoundException {
        // Get the course entity from the database
        Course course = courseService.getCourseByName(courseName);

        // Create a response with the file data
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(course.getFileData());
    }


    @PostMapping("/update/{courseName}")
    public ResponseEntity<Course> updateCourse(@RequestParam("name") String name,
                                          @RequestParam("newName") String newName,
                                          @RequestParam("niveau") String niveau,
                                          @RequestParam("description") String description,
                                          @RequestParam("type") String type,
                                          @RequestParam("duree") String duree,
                                          @RequestParam("etat") String etat,
                                          @RequestParam("quiz") String quiz,
                                          @RequestParam("ordre") int ordre,
                                          @RequestParam("formationName") String formationName) {

            Course courseUpdated = courseService.updateCourse(name,newName,niveau,description,type,duree,etat,quiz,ordre,formationName);
        return new ResponseEntity<>(courseUpdated, HttpStatus.CREATED);


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



   @GetMapping("/all")
    public ResponseEntity<List<Course>> getAllCourses() throws NotFoundException {
        List<Course> courses = courseService.getAllCourses();
        return ResponseEntity.ok(courses);
    }


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
