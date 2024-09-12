package com.DesAca.DesAca.Auth;

import java.util.Optional;
import org.springframework.stereotype.Service;

import com.DesAca.DesAca.Professor.Professor;
import com.DesAca.DesAca.Professor.ProfessorRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final ProfessorRepository professorRepository;
    public boolean login(String email, String password) {
        // Verificar si el profesor con el email existe
        Optional<Professor> professorOptional = professorRepository.findByEmail(email);

        if (professorOptional.isPresent()) {
            Professor professor = professorOptional.get();
            
            // Simulamos la verificación de la contraseña (en un caso real, se debe usar hashing)
            if (professor.getPassword().equals(password)) {
                return true; // Credenciales correctas
            }
        }

        return false; // Credenciales incorrectas
    }
}
