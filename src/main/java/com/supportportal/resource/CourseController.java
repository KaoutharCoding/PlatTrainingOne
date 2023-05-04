package com.supportportal.resource;

import com.supportportal.domain.Course;
import com.supportportal.domain.CourseRequestDTO;
import com.supportportal.exception.domain.FormationNameAlreadyExistsException;
import com.supportportal.service.CourseService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @PostMapping("/create")
    public ResponseEntity<Course> createCourse(@RequestBody CourseRequestDTO course) throws NotFoundException, FormationNameAlreadyExistsException {
        Course createdCourse = courseService.createCourse(course);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCourse);
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
    public ResponseEntity<List<Course>> getAllCourses() {
        List<Course> courses = courseService.getAllCourses();
        return ResponseEntity.ok(courses);
    }

    @DeleteMapping("/delete/{courseName}")
    public void deleteCourse(@PathVariable String courseName) throws NotFoundException {
        courseService.deleteCourse(courseName);

        /*try {
            courseService.deleteCourse(name);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }*/
    }
}
