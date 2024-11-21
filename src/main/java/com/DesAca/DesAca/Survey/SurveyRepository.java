package com.DesAca.DesAca.Survey;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SurveyRepository extends JpaRepository<Survey, Long> {
    List<Survey> findByProfessorId(Long professorId);

    List<Survey> findByProfessorIdAndCompleteFalse(Long professorId);

    @Query("SELECT s FROM Survey s WHERE s.course.id = :courseId")
    List<Survey> findByCourseId(@Param("courseId") Long courseId);

}





