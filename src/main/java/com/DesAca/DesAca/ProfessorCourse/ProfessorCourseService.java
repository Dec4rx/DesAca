package com.DesAca.DesAca.ProfessorCourse;

import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;

import com.DesAca.DesAca.Course.Course;
import com.DesAca.DesAca.Course.CourseRepository;
import com.DesAca.DesAca.Course.CourseSummaryDTO;
import com.DesAca.DesAca.Professor.Professor;
import com.DesAca.DesAca.Professor.ProfessorRepository;

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

        // Verificar la capacidad del curso
        if (course.getCapacity() <= 0) {
            throw new IllegalStateException("El curso ya no tiene capacidad disponible.");
        }

        // Verificar si la fecha de inicio del curso es hoy o una fecha posterior
        if (!course.getStartDate().isAfter(LocalDate.now())) {
            throw new IllegalStateException("No se puede inscribir en un curso que inicia hoy o ya ha iniciado.");
        }

        // Verificar si el curso ya está asignado al profesor
        boolean alreadyAssigned = professorCourseRepository.existsByProfessorAndCourse(professor, course);
        if (alreadyAssigned) {
            throw new IllegalStateException("El profesor ya está inscrito en este curso.");
        }

        // Crear la relación
        ProfessorCourse professorCourse = new ProfessorCourse();
        professorCourse.setProfessor(professor);
        professorCourse.setCourse(course);
        professorCourse.setFinished(isFinished);
        professorCourseRepository.save(professorCourse);

        // Reducir la capacidad del curso
        course.setCapacity(course.getCapacity() - 1);
        courseRepository.save(course);
    }

    public List<CourseSummaryDTO> getCoursesByProfessorId(Long professorId) {
        return professorCourseRepository_Courses.findCourseSummariesByProfessorId(professorId);
    }

    public List<CourseSummaryDTO> getFinishedCoursesByProfessorId(Long professorId) {
        return professorCourseRepository.findFinishedCoursesByProfessorId(professorId);
    }
}
