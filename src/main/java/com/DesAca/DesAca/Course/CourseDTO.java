package com.DesAca.DesAca.Course;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseDTO {

    @NotBlank(message = "El nombre del curso no puede ser nulo")
    @Size(min = 3, message = "El nombre del curso debe tener al menos 3 caracteres")
    private String courseName;

    @NotNull(message = "El ID del diagnóstico no puede ser nulo")
    private Long diagnosisId; // ID del diagnóstico asociado

    @NotNull(message = "La fecha de inicio no puede ser nula")
    @FutureOrPresent(message = "La fecha de inicio debe ser en el presente o futuro")
    private LocalDate startDate;

    @NotNull(message = "La fecha de fin no puede ser nula")
    @FutureOrPresent(message = "La fecha de fin debe ser en el presente o futuro")
    private LocalDate endDate;

    @NotBlank(message = "El horario no puede ser nulo")
    @Size(min = 3, message = "El horario debe tener al menos 3 caracteres")
    private String shift;

    @NotNull(message = "La capacidad no puede ser nula")
    @Min(value = 1, message = "La capacidad debe ser mayor a 0")
    private Integer capacity;

    @NotBlank(message = "Los requisitos no pueden ser nulos")
    private String requirements;

    @NotBlank(message = "El departamento no puede estar vacío")
    @Size(min = 3, message = "El departamento debe tener al menos 3 caracteres")
    private String departament;

    @NotBlank(message = "El público objetivo no puede estar vacío")
    private String aimedAt;

    @NotBlank(message = "El tipo de curso no puede estar vacío")
    private String type;

    @NotBlank(message = "El enfoque del curso no puede estar vacío")
    private String approach;

    @NotBlank(message = "La persona encargada de enseñar no puede estar vacía")
    private String personToTeach;

    @NotBlank(message = "La institución o academia no puede estar vacía")
    private String institutionOrAcademic;

    @NotNull(message = "El número de horas no puede ser nulo")
    @Min(value = 1, message = "El número de horas debe ser mayor a 0")
    private Integer numberHours;

    @NotBlank(message = "El lugar no puede estar vacío")
    private String place;

    @NotBlank(message = "La justificación no puede estar vacía")
    private String justification;

    @NotBlank(message = "El objetivo no puede estar vacío")
    private String objective;

    @NotBlank(message = "Los contenidos temáticos no pueden estar vacíos")
    private String thematicContents;

    @NotBlank(message = "Los recursos no pueden estar vacíos")
    private String resources;

    @NotBlank(message = "Las fuentes de información no pueden estar vacías")
    private String informationSources;

    @NotBlank(message = "La autorización no puede estar vacía")
    private String authorization;

    @NotBlank(message = "La revisión no puede estar vacía")
    private String review;
}
