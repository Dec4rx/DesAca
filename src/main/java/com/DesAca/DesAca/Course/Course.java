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
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "course_seq_gen")
    @SequenceGenerator(name = "course_seq_gen", sequenceName = "course_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "Nombre del curso no puede ser nulo")
    @Size(min = 3, message = "Nombre del curso debe tener al menos 3 caracteres")
    private String courseName;

    @OneToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, optional = false)
    @JoinColumn(name = "diagnosis_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fk_course_diagnosis"))
    @JsonIgnore
    private Diagnosis diagnosis;

    @Column(nullable = false)
    @NotNull(message = "La fecha de inicio no puede ser nula")
    @FutureOrPresent(message = "La fecha de inicio debe ser en el presente o futuro")
    private LocalDate startDate;

    @Column(nullable = false)
    @NotNull(message = "La fecha de fin no puede ser nula")
    @FutureOrPresent(message = "La fecha de fin debe ser en el presente o futuro")
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
    private String requirements;

    @OneToMany(mappedBy = "course")
    @JsonIgnore
    private List<ProfessorCourse> professorCourses;

    @Column(nullable = false)
    private boolean enabled;

    @Column(nullable = false)
    private LocalDate dateRegistration = LocalDate.now();

    @Column(nullable = false)
    @NotBlank(message = "El departamento no puede estar vacío")
    @Size(min = 3, message = "El departamento debe tener al menos 3 caracteres")
    private String departament;

    @Column(nullable = false)
    @NotBlank(message = "El público objetivo no puede estar vacío")
    private String aimedAt;

    @Column(nullable = false)
    @NotBlank(message = "El tipo de curso no puede estar vacío")
    private String type;

    @Column(nullable = false)
    @NotBlank(message = "El enfoque del curso no puede estar vacío")
    private String approach;

    @Column(nullable = false)
    @NotBlank(message = "La persona encargada de enseñar no puede estar vacía")
    private String personToTeach;

    @Column(nullable = false)
    @NotBlank(message = "La institución o academia no puede estar vacía")
    private String institutionOrAcademic;

    @Column(nullable = false)
    @Min(value = 1, message = "El número de horas debe ser mayor a 0")
    private int numberHours;

    @Column(nullable = false)
    @NotBlank(message = "El lugar no puede estar vacío")
    private String place;

    @Column(columnDefinition = "TEXT", nullable = false)
    @NotBlank(message = "La justificación no puede estar vacía")
    private String justification;

    @Column(columnDefinition = "TEXT", nullable = false)
    @NotBlank(message = "El objetivo no puede estar vacío")
    private String objective;

    @Column(columnDefinition = "TEXT", nullable = false)
    @NotBlank(message = "Los contenidos temáticos no pueden estar vacíos")
    private String thematicContents;

    @Column(columnDefinition = "TEXT", nullable = false)
    @NotBlank(message = "Los recursos no pueden estar vacíos")
    private String resources;

    @Column(columnDefinition = "TEXT", nullable = false)
    @NotBlank(message = "Las fuentes de información no pueden estar vacías")
    private String informationSources;

    @Column(nullable = false)
    @NotBlank(message = "La autorización no puede estar vacía")
    private String authorization;

    @Column(columnDefinition = "TEXT", nullable = false)
    @NotBlank(message = "La revisión no puede estar vacía")
    private String review;

    @Column(nullable = true)
    private String file1Path;

    @Column(nullable = true)
    private String file2Path;
}
