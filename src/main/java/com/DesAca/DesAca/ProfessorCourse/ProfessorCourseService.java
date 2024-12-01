package com.DesAca.DesAca.ProfessorCourse;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.DesAca.DesAca.Course.Course;
import com.DesAca.DesAca.Course.CourseRepository;
import com.DesAca.DesAca.Course.CourseSummaryDTO;
import com.DesAca.DesAca.Professor.Professor;
import com.DesAca.DesAca.Professor.ProfessorRepository;
import com.DesAca.DesAca.Survey.Survey;
import com.DesAca.DesAca.Survey.SurveyRepository;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProfessorCourseService {
    private final ProfessorCourseRepository professorCourseRepository;
    private final ProfessorCourseRepository_Courses professorCourseRepository_Courses;
    private final CourseRepository courseRepository;
    private final ProfessorRepository professorRepository;
    private final SurveyRepository surveyRepository;

    public void createProfessorCourse(long professor_id, long course_id, boolean isFinished, String statusProf) {

        // Buscar el profesor por su ID
        Professor professor = professorRepository.findById(professor_id)
                .orElseThrow(() -> new RuntimeException("Profesor no encontrado"));

        // Buscar el curso por su ID
        Course course = courseRepository.findById(course_id)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));

        // Verificar la capacidad del curso
        if (course.getCapacity() <= 0) {
            throw new IllegalStateException("El curso ya no tiene capacidad disponible.");
        }

        // Verificar si la fecha de inicio del curso es hoy o una fecha posterior
        if (!course.getStartDate().isAfter(LocalDate.now())) {
            throw new IllegalStateException("No se puede inscribir en un curso que inicia hoy o ya ha iniciado.");
        }

        // Verificar si el curso ya está asignado al profesor
        boolean alreadyAssigned = professorCourseRepository.existsByProfessorAndCourse(professor, course);
        if (alreadyAssigned) {
            throw new IllegalStateException("El profesor ya está inscrito en este curso.");
        }

        // Crear la relación
        ProfessorCourse professorCourse = new ProfessorCourse();
        professorCourse.setProfessor(professor);
        professorCourse.setCourse(course);
        professorCourse.setFinished(isFinished);
        professorCourse.setStatusProf(statusProf);
        professorCourseRepository.save(professorCourse);

        // Reducir la capacidad del curso
        course.setCapacity(course.getCapacity() - 1);
        courseRepository.save(course);

        // Crear la encuesta para el curso
        createSurvey(professor, course);
    }

    private void createSurvey(Professor professor, Course course) {
        Survey survey = new Survey();
        survey.setProfessor(professor);
        survey.setCourse(course);
        survey.setComplete(false); // Inicialmente, la encuesta no está completa
        surveyRepository.save(survey);
    }

    public List<CourseSummaryDTO> getCoursesByProfessorId(Long professorId) {
        return professorCourseRepository_Courses.findCourseSummariesByProfessorId(professorId);
    }

    public List<CourseSummaryDTO> getFinishedCoursesByProfessorId(Long professorId) {
        return professorCourseRepository.findFinishedCoursesByProfessorId(professorId);
    }

    public List<Professor> getProfessorsByCourseId(Long courseId) {
        List<ProfessorCourse> professorCourses = professorCourseRepository.findByCourseId(courseId);
        return professorCourses.stream()
                .map(ProfessorCourse::getProfessor)
                .collect(Collectors.toList());
    }

    public ProfessorCourse updateAttendancePercentage(Long professorCourseId, double percentage) {
        ProfessorCourse professorCourse = professorCourseRepository.findById(professorCourseId)
                .orElseThrow(() -> new RuntimeException("ProfesorCourse not found"));

        professorCourse.setAttendancePercentage(percentage);
        return professorCourseRepository.save(professorCourse);
    }

    @Transactional
    public ResponseEntity<InputStreamResource> generateAndDownloadCertificate(Long courseId, Long professorId)
            throws IOException {
        // Buscar la relación ProfessorCourse
        ProfessorCourse professorCourse = professorCourseRepository.findByCourseIdAndProfessorId(courseId, professorId)
                .orElseThrow(
                        () -> new IllegalArgumentException("No se encontró relación entre el curso y el profesor."));

        // Validar que el curso esté terminado
        if (!professorCourse.getCourse().isEnabled()) {
            throw new IllegalStateException("El curso no está terminado.");
        }

        // Validar que las evidencias estén guardadas
        if (professorCourse.getCourse().getPdfEvidencePath() == null) {
            throw new IllegalStateException("No se encontraron evidencias guardadas para el curso.");
        }

        // Validar que el profesor tenga al menos un 85% de asistencia
        if (professorCourse.getAttendancePercentage() < 85) {
            throw new IllegalStateException("El profesor no cumple con el porcentaje mínimo de asistencia (85%).");
        }

        // Determinar el folio
        String folio = (!professorCourse.getStatusProf().equalsIgnoreCase("Interinato Fuera de Fecha") &&
                !professorCourse.getStatusProf().equalsIgnoreCase("Honorarios"))
                        ? UUID.randomUUID().toString()
                        : "SIN FOLIO";

        // Obtener datos del curso y profesor
        Course course = professorCourse.getCourse();
        Professor professor = professorCourse.getProfessor();

        // Rellenar el PDF
        String templatePath = "C:/Users/HP/Desktop/Constancias/template_constancia.pdf";
        String outputPath = "C:/Users/HP/Desktop/Constancias/constancia_" + professor.getId() + "_" + course.getId()
                + ".pdf";

        try {
            fillPdf(templatePath, outputPath, professor, course, folio);
        } catch (Exception e) {
            // Manejo de la excepción
            e.printStackTrace(); // Para depuración
            throw new RuntimeException("Error al generar el PDF", e); // O lanza una excepción personalizada
        }

        // Actualizar la ruta de la constancia en la entidad ProfessorCourse
        professorCourse.setRouteConstancy(outputPath);
        professorCourseRepository.save(professorCourse);

        // Preparar el archivo para descarga
        File file = new File(outputPath);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName())
                .contentType(MediaType.APPLICATION_PDF)
                .contentLength(file.length())
                .body(resource);
    }

    public void fillPdf(String templatePath, String outputPath, Professor professor, Course course, String folio)
            throws Exception {
        // Crear el directorio si no existe
        File directory = new File("C:/Users/HP/Desktop/Constancias/");
        if (!directory.exists()) {
            directory.mkdirs();
        }
        PdfReader pdfReader = new PdfReader(templatePath);
        PdfStamper pdfStamper = new PdfStamper(pdfReader, new FileOutputStream(outputPath));
        AcroFields form = pdfStamper.getAcroFields();

        // Obtener el nombre del departamento desde el objeto Course o Professor
        String departmentName = professor.getDepartamentid() != null ? professor.getDepartamentid().getName()
                : "SIN DEPARTAMENTO";

        // Rellenar los campos
        form.setField("professorName",
                (professor.getName() + " " + professor.getMiddleName() + " " + professor.getLastName()));
        form.setField("courseName", course.getCourseName());
        form.setField("folio", folio);
        form.setField("department", departmentName);
        form.setField("startDate", course.getStartDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        form.setField("endDate", course.getEndDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

        // Cerrar el documento
        pdfStamper.setFormFlattening(true); // Hace que los campos sean no editables
        pdfStamper.close();
        pdfReader.close();
    }
}
