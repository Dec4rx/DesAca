package com.DesAca.DesAca.Professor;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProfessorDTO {
    private Long id;
    private String name;
    private String middleName;
    private String lastName;
    private String gender;
    private String status;    
}
