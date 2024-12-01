package com.DesAca.DesAca.Career;

import java.util.Set;

import com.DesAca.DesAca.Course.Course;
import com.DesAca.DesAca.Department.Department;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Career {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;  // El departamento al que pertenece esta carrera

    @ManyToMany(mappedBy = "careers")
    private Set<Course> courses;  // Cursos que ofrecen esta carrera
}
