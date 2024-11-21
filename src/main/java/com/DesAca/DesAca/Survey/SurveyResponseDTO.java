package com.DesAca.DesAca.Survey;

import com.DesAca.DesAca.Answer.AnswerDTO;
import lombok.Data;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Data
public class SurveyResponseDTO {
    private Long surveyId; // ID de la encuesta
    private List<AnswerDTO> answers; // Lista de respuestas
}
