package project.mozit.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import project.mozit.domain.Questions;
import project.mozit.dto.CustomUserDetails;
import project.mozit.dto.QuestionsDTO;
import project.mozit.service.QuestionsService;

import java.util.List;

@RestController
@RequestMapping("/questions")
@RequiredArgsConstructor
public class QuestionsController {

    public final QuestionsService questionsService;

    @PostMapping
    public ResponseEntity<Questions> insertQuestion(@RequestBody QuestionsDTO.Post dto){
        Questions savedQuestion = questionsService.insertQuestion(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedQuestion);
    }

    @GetMapping
    public List<QuestionsDTO.Response> getQuestions(){
        return questionsService.findQuestions();
    }

    @GetMapping("/my")
    public List<QuestionsDTO.Response> getMyQuestions(@AuthenticationPrincipal CustomUserDetails userDetails){
        if (userDetails == null) {
            throw new IllegalArgumentException("User is not authenticated");
        }

        String userId = userDetails.getUsername();
        return questionsService.findQuestionsByUser(userId);
    }
    
    @GetMapping("/{questionId}")
    public QuestionsDTO.Response getQuestion(@PathVariable("questionId") Long id){
        return questionsService.findQuestion(id);
    }

    @DeleteMapping("/{questionId}")
    public void deleteQuestion(@PathVariable("questionId") Long id){
        questionsService.deleteQuestion(id);
    }
}
