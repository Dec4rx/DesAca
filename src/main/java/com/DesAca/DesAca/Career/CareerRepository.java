package com.DesAca.DesAca.Career;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CareerRepository extends JpaRepository<Career, Long> {
    // También puedes definir métodos específicos para buscar carreras por departamento, por ejemplo:
    List<Career> findByDepartmentId(Long departmentId);
}

