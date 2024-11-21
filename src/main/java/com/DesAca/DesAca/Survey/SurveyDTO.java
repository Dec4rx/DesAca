package com.DesAca.DesAca.Survey;

import java.util.List;

import com.DesAca.DesAca.Question.QuestionDTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class SurveyDTO {
    private Long courseId;
    private Long professorId;
}
