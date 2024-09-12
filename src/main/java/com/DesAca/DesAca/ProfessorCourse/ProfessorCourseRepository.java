package com.DesAca.DesAca.ProfessorCourse;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.DesAca.DesAca.Course.Course;
import com.DesAca.DesAca.Course.CourseSummaryDTO;

@Repository
public interface ProfessorCourseRepository extends JpaRepository<ProfessorCourse, Long> {
// Consulta personalizada para obtener todos los cursos de un profesor por su ID
    @Query("SELECT pc.course FROM ProfessorCourse pc WHERE pc.professor.id = :professorId")
    List<Course> findAllCoursesByProfessorId(Long professorId);

   // Consulta corregida para obtener los cursos terminados por el profesor
   @Query("SELECT new com.DesAca.DesAca.Course.CourseSummaryDTO(c.id, c.courseName, c.startDate, c.endDate, c.shift, c.schedule, c.capacity, c.requirements, pc.isFinished) " +
   "FROM ProfessorCourse pc JOIN pc.course c " +
   "WHERE pc.professor.id = :professorId AND pc.isFinished = true")
List<CourseSummaryDTO> findFinishedCoursesByProfessorId(@Param("professorId") Long professorId);
}
