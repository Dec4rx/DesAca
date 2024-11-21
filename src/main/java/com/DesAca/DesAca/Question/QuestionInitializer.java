package com.DesAca.DesAca.Question;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.Arrays;
import java.util.List;

@Component
public class QuestionInitializer implements CommandLineRunner {

    private final QuestionRepository questionRepository;

    public QuestionInitializer(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Lista de preguntas fijas
        List<String> fixedQuestions = Arrays.asList(
            "¿Qué tan claros fueron los objetivos del curso?",
            "¿El contenido del curso fue relevante para tus necesidades?",
            "¿El profesor dominaba el tema?",
            "¿El profesor fue claro al explicar los conceptos?",
            "¿El material de apoyo fue útil?",
            "¿El curso cumplió tus expectativas?",
            "¿La duración del curso fue adecuada?",
            "¿El ritmo del curso fue adecuado?",
            "¿Qué tan accesible fue el profesor para resolver dudas?",
            "¿El curso fomentó la participación?",
            "¿El contenido del curso fue actualizado?",
            "¿El curso proporcionó ejemplos prácticos?",
            "¿El curso incluyó actividades útiles?",
            "¿Qué tan satisfecho estás con el curso en general?",
            "¿Recomendarías este curso a otros?",
            "¿Qué tan organizado estuvo el curso?",
            "¿El curso cumplió con los objetivos planteados?",
            "¿El ambiente de aprendizaje fue adecuado?",
            "¿Se respetaron los horarios del curso?",
            "¿Qué mejorarías en el curso?"
        );

        // Verificar si las preguntas ya existen
        if (questionRepository.count() == 0) {
            fixedQuestions.forEach(questionText -> {
                Question question = new Question();
                question.setText(questionText);
                questionRepository.save(question);
            });

            System.out.println("Preguntas iniciales insertadas en la base de datos.");
        } else {
            System.out.println("Las preguntas ya están inicializadas.");
        }
    }
}

