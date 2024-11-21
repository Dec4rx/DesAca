package com.DesAca.DesAca.Answer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AnswerDTO {
    private Long questionId; // ID de la pregunta
    private String response; // Respuesta del profesor // Respuesta dada por el profesor
}

