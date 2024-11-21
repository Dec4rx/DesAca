package com.DesAca.DesAca.Course;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    
    boolean existsByDiagnosisId(Long diagnosisId);

    @Query("SELECT c FROM Course c WHERE " +
       "(c.file1Path IS NOT NULL AND c.file1Path <> '') AND " +
       "(c.file2Path IS NOT NULL AND c.file2Path <> '') AND " +
       "c.id NOT IN (SELECT pc.course.id FROM ProfessorCourse pc)")
List<Course> findCoursesWithFilesNotAssignedToProfessor();
    
}
