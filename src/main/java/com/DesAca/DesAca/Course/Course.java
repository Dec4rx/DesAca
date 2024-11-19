package com.DesAca.DesAca.Course;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDate;
import java.util.List;
import com.DesAca.DesAca.ProfessorCourse.ProfessorCourse;
import com.DesAca.DesAca.Diagnosis.Diagnosis;

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
    @NotBlank(message = "El horario no puede ser nulo")
    @Size(min = 3, message = "El horario debe tener al menos 3 caracteres")
    private String shift;

    @Column(nullable = false)
    @NotNull(message = "La capacidad no puede ser nula")
    @Min(value = 1, message = "La capacidad debe ser mayor a 0")
    private int capacity;

    @Column(columnDefinition = "TEXT", nullable = false)
    @NotBlank(message = "Los requisitos no pueden ser nulos")
    @Size(min = 3, message = "Los requisitos deben tener al menos 3 caracteres")
    private String requirements;

    @OneToMany(mappedBy = "course")
    @JsonIgnore
    private List<ProfessorCourse> professorCourses;

    @Column(nullable = false)
    private boolean enabled;


    @Column(nullable = false)
    private LocalDate dateRegistration = LocalDate.now();  // Fecha de registro, se establece al momento actual

    @Column(nullable = false)
    @NotBlank
    private String departament;  // Departamento asociado

    @Column
    @NotBlank
    private String aimedAt;  // Dirigido a

    @Column
    @NotBlank
    private String type;  // Tipo de curso

    @Column
    @NotBlank
    private String approach;  // Enfoque del curso

    @Column
    @NotBlank
    private String personToTeach;  // Persona encargada de enseñar

    @Column
    @NotBlank
    private String institutionOrAcademic;  // Institución o academia

    @Column
    private int numberHours;  // Número de horas

    @Column
    @NotBlank
    private String place;  // Lugar

    @Column(columnDefinition = "TEXT")
    @NotBlank
    private String justification;  // Justificación

    @Column(columnDefinition = "TEXT")
    @NotBlank
    private String objective;  // Objetivo

    @Column(columnDefinition = "TEXT")
    @NotBlank
    private String thematicContents;  // Contenidos temáticos

    @Column(columnDefinition = "TEXT")
    @NotBlank
    private String resources;  // Recursos

    @Column(columnDefinition = "TEXT")
    @NotBlank
    private String informationSources;  // Fuentes de información

    @Column
    @NotBlank
    private String authorization;  // Autorización

    @Column(columnDefinition = "TEXT")
    @NotBlank
    private String review;  // Revisión

    // Relación uno-a-uno con Diagnosis
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diagnosis_id", referencedColumnName = "id", nullable = false)
    private Diagnosis diagnosis;
}
