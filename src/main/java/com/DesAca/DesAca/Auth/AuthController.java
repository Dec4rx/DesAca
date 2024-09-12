package com.DesAca.DesAca.Auth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.DesAca.DesAca.Professor.Professor;
import com.DesAca.DesAca.Professor.ProfessorDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    // Endpoint para simular login
    @PostMapping("/login")
    public ResponseEntity<ProfessorDTO> login(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");

        // Verificar si las credenciales son correctas
        Professor professor = authService.login(email, password);

        if (professor != null) {
            // Si el login es exitoso, retornar el DTO con la información del profesor
            ProfessorDTO professorDTO = new ProfessorDTO(
                    professor.getId(),
                    professor.getName(),
                    professor.getMiddleName(),
                    professor.getLastName(),
                    professor.getGender(),
                    professor.getStatus());
            return ResponseEntity.ok(professorDTO);
        } else {
            // Si las credenciales son incorrectas, retornar un UNAUTHORIZED
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

}
