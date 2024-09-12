package com.DesAca.DesAca.ProfessorCourse;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ProfessorCourseDTO {
    private Long professorId;
    private Long courseId;
    private boolean isFinished;
}

