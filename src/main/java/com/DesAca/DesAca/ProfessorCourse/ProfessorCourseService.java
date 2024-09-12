package com.DesAca.DesAca.ProfessorCourse;

import java.util.List;
import org.springframework.stereotype.Service;

import com.DesAca.DesAca.Course.Course;
import com.DesAca.DesAca.Course.CourseRepository;
import com.DesAca.DesAca.Course.CourseSummaryDTO;
import com.DesAca.DesAca.Professor.Professor;
import com.DesAca.DesAca.Professor.ProfessorRepository;
import com.DesAca.DesAca.ProfessorCourse.ProfessorCourseRepository_Courses;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProfessorCourseService {
    private final ProfessorCourseRepository professorCourseRepository;
    private final ProfessorCourseRepository_Courses professorCourseRepository_Courses;
    private final CourseRepository courseRepository;
    private final ProfessorRepository professorRepository;

    public void createProfessorCourse(long professor_id, long course_id, boolean isFinished) {

        // Buscar el profesor por su ID
        Professor professor = professorRepository.findById(professor_id)
                .orElseThrow(() -> new RuntimeException("Profesor no encontrado"));

        // Buscar el curso por su ID
        Course course = courseRepository.findById(course_id)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));

        // Crear la relación
        ProfessorCourse professorCourse = new ProfessorCourse();
        professorCourse.setProfessor(professor);
        professorCourse.setCourse(course);
        professorCourse.setFinished(isFinished);
        professorCourseRepository.save(professorCourse);

    }

    public List<CourseSummaryDTO> getCoursesByProfessorId(Long professorId) {
        return professorCourseRepository_Courses.findCourseSummariesByProfessorId(professorId);
    }

    public List<CourseSummaryDTO> getFinishedCoursesByProfessorId(Long professorId) {
        return professorCourseRepository.findFinishedCoursesByProfessorId(professorId);
    }
}
