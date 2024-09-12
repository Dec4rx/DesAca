package com.DesAca.DesAca.Course;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseSummaryDTO {
    private Long id;
    private String courseName;
    private LocalDate startDate;
    private LocalDate endDate;
    private String shift;
    private String schedule;
    private int capacity;
    private String requirements;
    private boolean isFinished;
}