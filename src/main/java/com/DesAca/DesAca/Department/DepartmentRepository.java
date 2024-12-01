package com.DesAca.DesAca.Department;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    // Aquí puedes agregar métodos de consulta personalizados si es necesario
}
