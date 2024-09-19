package com.DesAca.DesAca.Professor;

import java.util.List;
import com.DesAca.DesAca.ProfessorCourse.ProfessorCourse;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Professor {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "Nombre no puede ser nulo")
    @Size(min = 3, message = "Nombre debe tener al menos 3 caracteres")
    private String name;

    @Column(nullable = false)
    @NotBlank(message = "Apellido paterno no puede ser nulo")
    @Size(min = 3, message = "Apellido debe tener al menos 3 caracteres")
    private String middleName;

    @Column(nullable = false)
    @NotBlank(message = "Apellido Materno no puede ser nulo")
    @Size(min = 3, message = "Apellido debe tener al menos 3 caracteres")
    private String lastName;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "Correo no puede ser nulo")
    @Email(message = "Correo debe ser un correo electrónico válido")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@aguascalientes\\.tecnm\\.mx$", message = "El correo debe pertenecer al dominio @aguascalientes.tecnm.mx")
    private String email;

    @Column(nullable = false)
    @NotBlank(message = "Género no puede ser nulo")
    @Size(min = 3, message = "Genero debe tener al menos 3 caracteres")
    private String gender;

    @Column(nullable = false)
    @NotBlank(message = "RFC no puede ser nulo")
    @Size(min = 13, max = 13, message = "RFC debe tener 13 caracteres")
    private String rfc;

    @Column(nullable = false)
    @NotBlank(message = "CURP no puede ser nulo")
    @Size(min = 18, max = 18, message = "CURP debe tener 18 caracteres")
    private String curp;

    @Column(nullable = false)
    @NotBlank(message = "Status no puede ser nulo")
    @Size(min = 3, message = "Status debe tener al menos 3 caracteres")
    private String status;

    @Column()
    private String departament;

    @Column(nullable = false)
    @NotBlank(message = "Contraseña no puede ser nula")
    @Size(min = 8, message = "Contraseña debe tener al menos 8 caracteres")
    private String password;

    @OneToMany(mappedBy = "professor")
    @JsonIgnore // Evita la recursión al ignorar la lista de 'professorCourses' en la serialización
    @JsonBackReference // Evita la recursión al ignorar la lista de 'professorCourses' en la serialización
    private List<ProfessorCourse> professorCourses;

}
