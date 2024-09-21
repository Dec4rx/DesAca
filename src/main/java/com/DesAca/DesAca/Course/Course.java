package com.DesAca.DesAca.Course;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnore;


import java.util.List;

import com.DesAca.DesAca.ProfessorCourse.ProfessorCourse;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Course {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "Nombre del curso no puede ser nulo")
    @Size(min = 3, message = "Nombre del curso debe tener al menos 3 caracteres")
    private String courseName;

    @Column(nullable = false)
    @NotNull(message = "La fecha de inicio no puede ser nula")
    @FutureOrPresent(message = "La fecha de inicio debe ser en el presente o futuro")
    private LocalDate startDate;

    @Column(nullable = false)
    @NotNull(message = "La fecha de inicio no puede ser nula")
    @FutureOrPresent(message = "La fecha de inicio debe ser en el presente o futuro")
    private LocalDate endDate;

    @Column(nullable = false)
    @NotBlank(message = "El turno no puede ser nulo")
    @Size(min = 3, message = "El turno debe tener al menos 3 caracteres")
    private String shift;

    @Column(nullable = false)
    @NotBlank(message = "El horario no puede ser nulo")
    @Size(min = 3, message = "El horario debe tener al menos 3 caracteres")
    private String schedule;

    @Column(nullable = false)
    @NotNull(message = "La capacidad no puede ser nula")
    @Min(value = 1, message = "La capacidad debe ser mayor a 0")
    private int capacity;

    @Column(columnDefinition = "TEXT" , nullable = false)
    @NotBlank(message = "Los requisitos no pueden ser nulos")
    @Size(min = 3, message = "Los requisitos deben tener al menos 3 caracteres")
    private String requirements;

    @OneToMany(mappedBy = "course")
    @JsonIgnore
    private List<ProfessorCourse> professorCourses;

    @Column(nullable = false)
    private boolean enabled;
}
