package com.DesAca.DesAca.Course;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.DesAca.DesAca.Diagnosis.Diagnosis;
import com.DesAca.DesAca.Diagnosis.DiagnosisRepository;
import com.DesAca.DesAca.Diagnosis.DiagnosisService;
import com.DesAca.DesAca.ProfessorCourse.ProfessorCourseRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseService {
    private static final Logger logger = LoggerFactory.getLogger(CourseService.class);

    private final CourseRepository courseRepository;
    private final DiagnosisRepository diagnosisRepository;
    private final ProfessorCourseRepository professorCourseRepository;

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

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    // TO DO Implementar lógica para obtener los cursos no asignados a un profesor
    public List<Course> getCoursesNotAssignedToProfessor() {
        return courseRepository.findAll();
    }

    // Directorio de subida configurado desde application.properties
    @Value("${file.upload-dir}")
    private String uploadDir;

    @Transactional
    public void saveCourseFiles(Long courseId, MultipartFile file1, MultipartFile file2) throws IOException {
        // Verificar si el curso existe
        Optional<Course> courseOptional = courseRepository.findById(courseId);
        if (courseOptional.isEmpty()) {
            throw new IllegalArgumentException("El curso no existe.");
        }

        Course course = courseOptional.get();

        // Crear la carpeta de almacenamiento si no existe
        String folderPath = uploadDir + "/" + courseId;
        Path folder = Paths.get(folderPath);
        if (!Files.exists(folder)) {
            Files.createDirectories(folder);
            logger.info("Directorio creado: {}", folderPath);
        }

        // Guardar los archivos en la carpeta
        Path file1Path = folder.resolve(file1.getOriginalFilename());
        Path file2Path = folder.resolve(file2.getOriginalFilename());

        try {
            Files.copy(file1.getInputStream(), file1Path, StandardCopyOption.REPLACE_EXISTING);
            Files.copy(file2.getInputStream(), file2Path, StandardCopyOption.REPLACE_EXISTING);
            logger.info("Archivos guardados correctamente: {}, {}", file1Path, file2Path);
        } catch (IOException e) {
            logger.error("Error al guardar los archivos: {}", e.getMessage());
            throw new IOException("Error al guardar los archivos.", e);
        }

        // Guardar rutas relativas en la base de datos
        String relativeFile1Path = "/uploads/" + courseId + "/" + file1.getOriginalFilename();
        String relativeFile2Path = "/uploads/" + courseId + "/" + file2.getOriginalFilename();

        course.setFile1Path(relativeFile1Path);
        course.setFile2Path(relativeFile2Path);

        courseRepository.save(course);
        logger.info("Archivos registrados en la base de datos para el curso con ID: {}", courseId);
    }

    public List<Course> getAvailableCourses() {
    LocalDate today = LocalDate.now();

    // Buscar cursos que tengan ambos archivos cargados, no estén en `professor_courses` y cuya fecha de inicio sea anterior a hoy
    return courseRepository.findAll().stream()
            .filter(course -> course.getFile1Path() != null && !course.getFile1Path().isBlank()) // Validar que file1 esté cargado
            .filter(course -> course.getFile2Path() != null && !course.getFile2Path().isBlank()) // Validar que file2 esté cargado
            .filter(course -> !professorCourseRepository.existsByCourse(course)) // Validar que no esté en `professor_courses`
            .filter(course -> course.getStartDate().isBefore(today)) // Validar que la fecha de inicio sea anterior a hoy
            .filter(course -> course.getCapacity() != 0)
            .collect(Collectors.toList());
}
}
