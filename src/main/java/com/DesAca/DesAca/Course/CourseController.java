package com.DesAca.DesAca.Course;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/course")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @PostMapping
    public ResponseEntity<Course> createCourse(@Valid @RequestBody Course course) {
        Course createdCourse = courseService.createCourse(course);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCourse);
    }

    // @GetMapping
    // public ResponseEntity<List<Course>>

    //TO DO Modify the endpoint to return the courses not assigned to a professor
    @GetMapping("/not-assigned")
    public ResponseEntity<List<Course>> getCoursesNotAssignedToProfessor() {
        List<Course> courses = courseService.getCoursesNotAssignedToProfessor();
        return ResponseEntity.ok(courses);
    }

}
