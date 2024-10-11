package com.DesAca.DesAca.Authorizer;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Authorizer {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "Nombre no puede ser nulo")
    @Size(min = 3, message = "Nombre debe tener al menos 3 caracteres")
    private String name;

    @Column(nullable = false)
    @NotBlank(message = "Nombre no puede ser nulo")
    @Size(min = 3, message = "Nombre debe tener al menos 3 caracteres")
    private String position;

    
    @Column(nullable = false, unique = true)
    @NotBlank(message = "No puedes tener clave nula")
    @Size(min=3, message = "No puedes tener clave nula")
    private String authKey;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "Correo no puede ser nulo")
    @Email(message = "Correo debe ser un correo electrónico válido")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@aguascalientes\\.tecnm\\.mx$", message = "El correo debe pertenecer al dominio @aguascalientes.tecnm.mx")
    private String email;

    @Column(nullable = false)
    @NotBlank(message = "Contraseña no puede ser nula")
    @Size(min = 8, message = "Contraseña debe tener al menos 8 caracteres")
    private String password;
}
