package com.DesAca.DesAca.Department;

import java.util.Set;

import com.DesAca.DesAca.Career.Career;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "departments")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

     @OneToMany(mappedBy = "department")
    private Set<Career> careers;  // Carreras que pertenecen a este departamento

    // Constructor, getters y setters ya incluidos con @Data
}
