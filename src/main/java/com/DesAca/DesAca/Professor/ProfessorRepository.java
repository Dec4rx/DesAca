package com.DesAca.DesAca.Professor;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Long> {
 // Definir el método personalizado para buscar por email
    Optional<Professor> findByEmail(String email);
}
