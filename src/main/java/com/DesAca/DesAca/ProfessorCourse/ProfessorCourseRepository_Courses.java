package com.DesAca.DesAca.ProfessorCourse;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.DesAca.DesAca.Course.CourseSummaryDTO;

import java.util.List;

@Repository
public interface ProfessorCourseRepository_Courses extends CrudRepository<ProfessorCourse, Long> {

    @Query("SELECT new com.DesAca.DesAca.Course.CourseSummaryDTO(c.id, c.courseName, c.startDate, c.endDate, c.shift, c.schedule, c.capacity, c.requirements, pc.isFinished) "
            +
            "FROM ProfessorCourse pc JOIN pc.course c WHERE pc.professor.id = :professorId")
    List<CourseSummaryDTO> findCourseSummariesByProfessorId(@Param("professorId") Long professorId);

}