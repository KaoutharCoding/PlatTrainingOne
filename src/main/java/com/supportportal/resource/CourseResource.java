package com.supportportal.resource;

import com.supportportal.domain.Course;
import com.supportportal.domain.HttpResponse;
import com.supportportal.domain.User;
import com.supportportal.domain.UserPrincipal;
import com.supportportal.enumeration.Etat;
import com.supportportal.enumeration.Languages;
import com.supportportal.enumeration.Level;
import com.supportportal.enumeration.Type;
import com.supportportal.exception.ExceptionHandling;
import com.supportportal.exception.domain.*;
import com.supportportal.service.CourseService;
import com.supportportal.service.UserService;
import com.supportportal.utility.JWTTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static com.supportportal.constant.FileConstant.*;
import static com.supportportal.constant.SecurityConstant.JWT_TOKEN_HEADER;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_PDF;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;


@RestController
@RequestMapping(path = { "/", "/course"})
@CrossOrigin(origins = "http://localhost:4200")
public class CourseResource extends ExceptionHandling {
    public static final String EMAIL_SENT = "An email with a new password was sent to: ";
    public static final String COURSE_DELETED_SUCCESSFULLY = "Course deleted successfully";
    private CourseService courseService;

    @Autowired
    public CourseResource( CourseService courseService) {
        this.courseService = courseService;
    }




    @PostMapping("/add")
    public ResponseEntity<Course> addNewCourse(@RequestParam("title")String title,
                                               @RequestParam("description")String description,
                                               @RequestParam("type") Type type,
                                               @RequestParam("duration")String duration,
                                               @RequestParam("language") Languages language,
                                               @RequestParam("level") Level level,
                                               @RequestParam("category") String category,
                                               @RequestParam(value = "profileImage" , required = false) MultipartFile profileImage) throws IOException, NotAnImageFileException, UserNotFoundException, UsernameExistException, EmailExistException {

        Course newCourse = courseService.addNewCourse(title,description,type,duration,level,language,category,profileImage);
        return new ResponseEntity<>(newCourse, OK);
    }

    @PostMapping("/update")
    public ResponseEntity<Course> updateCourse(@RequestParam("currentTitle")String currentTitle,
                                               @RequestParam("title")String title,
                                               @RequestParam("description")String description,
                                               @RequestParam("type") Type type,
                                               @RequestParam("duration")String duration,
                                               @RequestParam("language") Languages language,
                                               @RequestParam("level") Level level,
                                               @RequestParam("category") String category,
                                               @RequestParam(value = "profileImage" , required = false) MultipartFile profileImage) throws IOException, UserNotFoundException, UsernameExistException, NotAnImageFileException, EmailExistException {

        Course updatedUser = courseService.updateCourse(currentTitle,title,description,type,duration,level,language,category,profileImage);
        return new ResponseEntity<>(updatedUser, OK);
    }

    @GetMapping("/find/{title}")
    public ResponseEntity<Course> getUser(@PathVariable("title") String title) {
        Course course = courseService.findCourseByTitle(title);
        return new ResponseEntity<>(course, OK);
    }

    @GetMapping("/list")
    public ResponseEntity<List<Course>> getAllCourses() {
        List<Course> users = courseService.getCourses();
        return new ResponseEntity<>(users, OK);
    }


    @DeleteMapping("/delete/{title}")
    @PreAuthorize("hasAnyAuthority('user:delete')")
    public ResponseEntity<HttpResponse> deleteCourse(@PathVariable("title") String title) throws IOException {
        courseService.deleteCourse(title);
        return response(OK, COURSE_DELETED_SUCCESSFULLY);
    }

    @PostMapping("/updateProfileImage")
    public ResponseEntity<Course> updateProfileImage(@RequestParam("title") String title, @RequestParam(value = "profileImage") MultipartFile profileImage) throws UserNotFoundException, UsernameExistException, EmailExistException, IOException, NotAnImageFileException, TitleExistException, CourseNotFoundException {
        Course course = courseService.updateProfileImage(title, profileImage);
        return new ResponseEntity<>(course, OK);
    }

    @GetMapping(path = "/image/{title}/{fileName}", produces = IMAGE_JPEG_VALUE)
    public byte[] getProfileImage(@PathVariable("title") String username, @PathVariable("fileName") String fileName) throws IOException {
        return Files.readAllBytes(Paths.get(USER_FOLDER + username + FORWARD_SLASH + fileName));
    }

    @GetMapping(path = "/image/profile/{title}", produces = IMAGE_JPEG_VALUE)
    public byte[] getTempProfileImage(@PathVariable("title") String title) throws IOException {
        URL url = new URL(TEMP_PROFILE_IMAGE_BASE_URL + title);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (InputStream inputStream = url.openStream()) {
            int bytesRead;
            byte[] chunk = new byte[1024];
            while((bytesRead = inputStream.read(chunk)) > 0) {
                byteArrayOutputStream.write(chunk, 0, bytesRead);
            }
        }
        return byteArrayOutputStream.toByteArray();
    }

    private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(),
                message), httpStatus);
    }



}
