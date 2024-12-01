package com.DesAca.DesAca.Department;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.DesAca.DesAca.Career.Career;
import com.DesAca.DesAca.Career.CareerRepository;
import com.DesAca.DesAca.Department.DepartmentRepository;

import jakarta.annotation.PostConstruct;

@Service
public class CareerDepartmentInitializer {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private CareerRepository careerRepository;

    @PostConstruct
    public void initializeData() {
        // Definición de departamentos y carreras
        Object[][] data = {
            {"Departamento de Ingeniería Industrial", new String[]{"Ingeniería Industrial"}},
            {"Departamento Metal-Mecánica", new String[]{"Ingeniería Mecánica", "Ingeniería en Materiales", "Ingeniería en Semiconductores"}},
            {"Departamento de Sistemas y Computación", new String[]{"Ingeniería en Tecnologías de la Información y Comunicaciones", "Ingeniería en Ciberseguridad"}},
            {"Departamento de Desarrollo Académico", new String[]{"No aplica"}},
            {"Departamento de Ingeniería Eléctrica – Electrónica", new String[]{"Ingeniería Eléctrica", "Ingeniería Electrónica"}},
            {"Departamento de Ciencias Básicas", new String[]{"No aplica"}},
            {"Departamento de Ciencias Económico – Administrativas", new String[]{"Ingeniería en Gestión Empresarial", "Licenciatura en Administración"}},
            {"Departamento de Ingeniería Química y Bioquímica", new String[]{"Ingeniería Química"}}
        };

        // Proceso de creación
        for (Object[] entry : data) {
            String departmentName = (String) entry[0];
            String[] careers = (String[]) entry[1];
        
            Department department = new Department();
            department.setName(departmentName);
            Department finalDepartment = departmentRepository.save(department);
        
            Arrays.stream(careers).forEach(careerName -> {
                Career career = new Career();
                career.setName(careerName);
                career.setDepartment(finalDepartment);
                careerRepository.save(career);
            });
        }
    }

}
