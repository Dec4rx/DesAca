package com.DesAca.DesAca.Diagnosis;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
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
    private String departament;

    @Column(nullable = false)
    @NotBlank(message = "La Fecha del Diagnóstico no puede ser nula")
    private LocalDate dateDiagnosis;

    @Column(nullable = false)
    @NotBlank(message = "El Titular del departamento no puede ser nulo")
    private String headDepartment;

    @Column(nullable = false)
    @NotBlank(message = "El Presidente de la academia no puede ser nulo")
    private String presidentAcademy;

    @Column(nullable = false)
    @NotBlank(message = "El Titular de la subdireción no puede ser nulo")
    private String titleSubdirectorate;

    @Column(columnDefinition = "TEXT", nullable = false)
    @NotBlank(message = "El Diagnóstico no puede ser nulo")
    private String requiredSubjects;

    @Column(columnDefinition = "TEXT", nullable = false)
    @NotBlank(message = "El Diagnóstico no puede ser nulo")
    private String thematicContents;

    
    




}
