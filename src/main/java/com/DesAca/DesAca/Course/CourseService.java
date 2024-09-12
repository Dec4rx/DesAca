package com.DesAca.DesAca.Course;

import java.util.List;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;

    public Course createCourse(Course course) {
        courseRepository.save(course);
        return course;
    }

    // TODO Implementar lógica para obtener los cursos no asignados a un profesor
    public List<Course> getCoursesNotAssignedToProfessor() {
        return courseRepository.findAll();
    }

}
