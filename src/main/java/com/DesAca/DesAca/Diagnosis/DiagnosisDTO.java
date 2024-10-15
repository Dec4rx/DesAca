package com.DesAca.DesAca.Diagnosis;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiagnosisDTO {
    private Long id;
    private String departament;
    private LocalDate dateDiagnosis;
    private String headDepartment;
    private String presidentAcademy;
    private String titleSubdirectorate;
    private String requiredSubjects;
    private String thematicContents;
    private int numberProfessors;
    private String typeSubject;
    private String activityEvent;
    private String objective;
    private String careersAttended;
    private String period;
    private String shift;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    private String facilitators;
    private String feedback;
    private boolean isAuthorizedByFirst;
    private boolean isAuthorizedBySecond;
}
