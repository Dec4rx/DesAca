package com.DesAca.DesAca.Question;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;
    public List<Question> getAllQuestions(){
        return questionRepository.findAll();
    }

}
