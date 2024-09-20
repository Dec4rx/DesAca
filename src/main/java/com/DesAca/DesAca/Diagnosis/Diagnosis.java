package com.DesAca.DesAca.Diagnosis;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Diagnosis {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "El Departamento no puede ser nulo")
    @Size(min = 3, message = "El Titular Departamento deben tener al menos 3 caracteres")
    private String departament;

    @Column(nullable = false)
    @NotNull(message = "La Fecha del Diagnóstico no puede ser nula")
    private LocalDate dateDiagnosis;

    @Column(nullable = false)
    @NotBlank(message = "El Titular del departamento no puede ser nulo")
    @Size(min = 3, message = "El Titular del departamento deben tener al menos 3 caracteres")
    private String headDepartment;

    @Column(nullable = false)
    @NotBlank(message = "El Presidente de la academia no puede ser nulo")
    @Size(min = 3, message = "El Presidente de la academia deben tener al menos 3 caracteres")
    private String presidentAcademy;

    @Column(nullable = false)
    @NotBlank(message = "El Titular de la subdireción no puede ser nulo")
    @Size(min = 3, message = "El Titular de la subdireción deben tener al menos 3 caracteres")
    private String titleSubdirectorate;

    @Column(columnDefinition = "TEXT", nullable = false)
    @NotBlank(message = "Las Asignaturas requeridas no pueden ser nulas")
    @Size(min = 10, message = "Las Asignaturas requeridas deben tener al menos 10 caracteres")
    private String requiredSubjects;

    @Column(columnDefinition = "TEXT", nullable = false)
    @NotBlank(message = "El Contenido tematico no puede ser nulo")
    @Size(min = 10, message = "El Contenido tematico debe tener al menos 10 caracteres")
    private String thematicContents;

    @Column(nullable = false)
    @NotNull(message = "El Número de profesores no puede ser nulo")
    @Min(value = 1, message = "El Número de profesores debe ser al menos 1")
    private int numberProfessors;

    @Column(nullable = false)
    @NotBlank(message = "El Tipo de Asignatura no puede ser nula")
    @Size(min = 7, message = "El Tipo de Asignatura debe tener al menos 7 caracteres")
    private String typeSubject;

    @Column(nullable = false)
    @NotBlank(message = "La Actividad/Evento no puede ser nula")
    @Size(min = 3, message = "La Actividad/Evento debe tener al menos 3 caracteres")
    private String activityEvent;


    @Column(columnDefinition = "TEXT", nullable = false)
    @NotBlank(message = "El Objetivo no puede ser nulo")
    @Size(min = 10, message = "El Objetivo debe tener al menos 10 caracteres")
    private String objective;


    @Column(nullable = false)
    @NotBlank(message = "Las carreras atendidas no puede ser nulas")
    @Size(min = 4, message = "Las carreras atendidas deben tener al menos 4 caracteres")
    private String careersAttended;

    @Column(nullable = false)
    @NotBlank(message = "El Periodo no puede ser nulo")
    @Size(min = 3, message = "El Periodo debe tener al menos 3 caracteres")
    private String period;
    
    @Column(nullable = false)
    @NotBlank(message = "El Turno no puede ser nulo")
    @Size(min = 3, message = "El Turno debe tener al menos 3 caracteres")
    private String shift;

    @Column(nullable = false)
    @NotNull(message = "La Fecha de Inicio no puede ser nula")
    @FutureOrPresent(message = "La Fecha de Inicio debe ser en el presente o futuro")
    private LocalDate starDate;

    @Column(nullable = false)
    @NotNull(message = "La Fecha de Fin no puede ser nula")
    @FutureOrPresent(message = "La Fecha de Fin debe ser en el presente o futuro")
    private LocalDate endDate;

    @Column(nullable = false)
    @NotBlank(message = "El Estado no puede ser nulo")
    @Size(min = 3, message = "El Estado debe tener al menos 3 caracteres")
    private String status;

    @Column(columnDefinition = "TEXT", nullable = false)
    @NotBlank(message = "El Facilitador no puede ser nulo")
    @Size(min = 3, message = "El Facilitador debe tener al menos 3 caracteres")
    private String facilitators;


}
