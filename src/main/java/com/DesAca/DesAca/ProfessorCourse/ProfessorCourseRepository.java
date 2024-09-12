package com.DesAca.DesAca.ProfessorCourse;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.DesAca.DesAca.Course.Course;

@Repository
public interface ProfessorCourseRepository extends JpaRepository<ProfessorCourse, Long> {
// Consulta personalizada para obtener todos los cursos de un profesor por su ID
    @Query("SELECT pc.course FROM ProfessorCourse pc WHERE pc.professor.id = :professorId")
    List<Course> findAllCoursesByProfessorId(Long professorId);
}
