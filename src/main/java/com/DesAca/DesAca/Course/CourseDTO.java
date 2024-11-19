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
    @NotNull(message = "El nombre del curso no puede ser nulo")
    @Size(min = 3, message = "El nombre del curso debe tener al menos 3 caracteres")
    private String courseName;

    @NotNull(message = "La fecha de inicio no puede ser nula")
    @FutureOrPresent(message = "La fecha de inicio debe ser en el presente o futuro")
    private LocalDate startDate;

    @NotNull(message = "La fecha de término no puede ser nula")
    @FutureOrPresent(message = "La fecha de término debe ser en el presente o futuro")
    private LocalDate endDate;

    @NotNull(message = "El turno no puede ser nulo")
    @Size(min = 3, message = "El turno debe tener al menos 3 caracteres")
    private String shift;

    @NotNull(message = "La capacidad no puede ser nula")
    @Min(value = 1, message = "La capacidad debe ser mayor a 0")
    private int capacity;

    @NotNull(message = "Los requisitos no pueden ser nulos")
    @Size(min = 3, message = "Los requisitos deben tener al menos 3 caracteres")
    private String requirements;

    @NotBlank(message = "El departamento no puede ser nulo")
    private String departament;

    @NotBlank(message = "El campo 'Dirigido a' no puede ser nulo")
    private String aimedAt;

    @NotBlank(message = "El tipo de curso no puede ser nulo")
    private String type;

    @NotBlank(message = "El enfoque del curso no puede ser nulo")
    private String approach;

    @NotBlank(message = "La persona encargada de impartir no puede ser nula")
    private String personToTeach;

    @NotBlank(message = "La institución o academia no puede ser nula")
    private String institutionOrAcademic;

    @Min(value = 1, message = "El número de horas debe ser mayor a 0")
    private int numberHours;

    @NotBlank(message = "El lugar no puede ser nulo")
    private String place;

    @NotBlank(message = "La justificación no puede ser nula")
    private String justification;

    @NotBlank(message = "El objetivo no puede ser nulo")
    private String objective;

    @NotBlank(message = "Los contenidos temáticos no pueden ser nulos")
    private String thematicContents;

    @NotBlank(message = "Los recursos no pueden ser nulos")
    private String resources;

    @NotBlank(message = "Las fuentes de información no pueden ser nulas")
    private String informationSources;

    @NotBlank(message = "El campo de autorización no puede ser nulo")
    private String authorization;

    @NotBlank(message = "El campo de revisión no puede ser nulo")
    private String review;

    @NotNull(message = "El ID del diagnóstico no puede ser nulo")
    private Long diagnosisId; // ID del diagnóstico asociado
}
