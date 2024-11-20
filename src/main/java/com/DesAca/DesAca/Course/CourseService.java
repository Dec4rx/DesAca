package com.DesAca.DesAca.Course;

import java.util.List;
import org.springframework.stereotype.Service;

import com.DesAca.DesAca.Diagnosis.Diagnosis;
import com.DesAca.DesAca.Diagnosis.DiagnosisRepository;
import com.DesAca.DesAca.Diagnosis.DiagnosisService;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@RequiredArgsConstructor
public class CourseService {
    private static final Logger logger = LoggerFactory.getLogger(CourseService.class);

    private final CourseRepository courseRepository;
    private final DiagnosisRepository diagnosisRepository;

    @Transactional
    public Course createCourse(CourseDTO courseDTO) {
        logger.info("Iniciando la creación del curso con datos: {}", courseDTO);

        // Validar que el diagnóstico existe
        Diagnosis diagnosis = diagnosisRepository.findById(courseDTO.getDiagnosisId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Diagnosis no encontrado con ID: " + courseDTO.getDiagnosisId()));

        logger.info("Diagnosis encontrado: {}", diagnosis);

        // Validar si el diagnóstico ya está asociado a otro curso (opcional)
        if (courseRepository.existsByDiagnosisId(courseDTO.getDiagnosisId())) {
            throw new IllegalStateException("El diagnóstico ya está asociado a otro curso.");
        }

        // Crear y configurar la entidad Course
        Course course = new Course();
        course.setDiagnosis(diagnosis);
        course.setCourseName(courseDTO.getCourseName());
        course.setAimedAt(courseDTO.getAimedAt());
        course.setType(courseDTO.getType());
        course.setApproach(courseDTO.getApproach());
        course.setPersonToTeach(courseDTO.getPersonToTeach());
        course.setDepartament(courseDTO.getDepartament());
        course.setRequirements(courseDTO.getRequirements());
        course.setStartDate(courseDTO.getStartDate());
        course.setEndDate(courseDTO.getEndDate());
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
        course.setEnabled(true); // Por defecto habilitado

        logger.info("Curso creado localmente, listo para persistir: {}", course);

        try {
            // Persistir el curso en la base de datos
            Course savedCourse = courseRepository.save(course);
            logger.info("Curso guardado exitosamente con ID: {}", savedCourse.getId());
            return savedCourse;
        } catch (Exception e) {
            logger.error("Error al guardar el curso: {}", e.getMessage(), e);
            throw new RuntimeException("Error al guardar el curso: " + e.getMessage(), e);
        }
    }

    // TO DO Implementar lógica para obtener los cursos no asignados a un profesor
    public List<Course> getCoursesNotAssignedToProfessor() {
        return courseRepository.findAll();
    }

}
