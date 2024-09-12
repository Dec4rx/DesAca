package com.DesAca.DesAca.ProfessorCourse;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.DesAca.DesAca.Professor.Professor;
import com.DesAca.DesAca.Course.Course;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "professor_courses")
public class ProfessorCourse {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "professor_id")
    private Professor professor;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @Column()
    private String routeConstancy;

    @Column(nullable = false)
    private boolean isFinished;
}

