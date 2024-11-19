package com.DesAca.DesAca.Course;

import java.util.List;
import org.springframework.stereotype.Service;

import com.DesAca.DesAca.Diagnosis.Diagnosis;
import com.DesAca.DesAca.Diagnosis.DiagnosisRepository;
import com.DesAca.DesAca.Diagnosis.DiagnosisService;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final DiagnosisRepository diagnosisRepository; // Suponiendo que tienes un repositorio para Diagnosis
    private final DiagnosisService diagnosisService;

    @Transactional
    public Course createCourse(CourseDTO courseDTO) {
        // Busca el diagnóstico por ID
        Diagnosis diagnosis = diagnosisService.findDiagnosisById(courseDTO.getDiagnosisId());
    
        // Crea una nueva entidad Course y asigna el Diagnosis
        Course course = new Course();
        course.setDiagnosis(diagnosis); // Asocia el Diagnosis
        course.setCourseName(courseDTO.getCourseName());
        course.setAimedAt(courseDTO.getAimedAt());
        course.setType(courseDTO.getType());
        course.setApproach(courseDTO.getApproach());
        course.setPersonToTeach(courseDTO.getPersonToTeach());
        course.setInstitutionOrAcademic(courseDTO.getInstitutionOrAcademic());
        course.setShift(courseDTO.getShift());
        course.setCapacity(courseDTO.getCapacity());
        course.setNumberHours(courseDTO.getNumberHours());
        course.setPlace(courseDTO.getPlace());
        course.setJustification(courseDTO.getJustification());
        course.setObjective(courseDTO.getObjective());
        course.setThematicContents(courseDTO.getThematicContents());
        course.setResources(courseDTO.getResources());
        course.setInformationSources(courseDTO.getInformationSources());
        course.setAuthorization(courseDTO.getAuthorization());
        course.setReview(courseDTO.getReview());
    
        // Guarda el curso en la base de datos
        return courseRepository.save(course);
    }
    

    // TODO Implementar lógica para obtener los cursos no asignados a un profesor
    public List<Course> getCoursesNotAssignedToProfessor() {
        return courseRepository.findAll();
    }

}
