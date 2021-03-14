package com.StackOverflow.StackOverflow.controller;

import com.StackOverflow.StackOverflow.dto.question.PostQuestionRequest;
import com.StackOverflow.StackOverflow.dto.question.PutQuestionRequest;
import com.StackOverflow.StackOverflow.mapper.QuestionMapper;
import com.StackOverflow.StackOverflow.model.Question;
import com.StackOverflow.StackOverflow.service.QuestionService;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/question")
@Api(value = "/question",
        tags = "Question Controller")
public class QuestionController {

    private final QuestionService questionService;
    private final QuestionMapper questionMapper;

    public QuestionController(QuestionService questionService, QuestionMapper questionMapper) {
        this.questionMapper = questionMapper;
        this.questionService = questionService;
    }

    @PostMapping("/add")
    @ApiOperation(value = "Add a question",
            notes = "Add a question based on the information received from request")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "The Question was successfully added based on the received request"),
            @ApiResponse(code = 400, message = "Validation error on the received request"),
            @ApiResponse(code = 417, message = "Information provided in the request doesn't not meet the condition.")
    })
    public ResponseEntity<Object> AddQuestion(
            @RequestBody
            @ApiParam(name = "QuestionObject", value = "Question Object", required = true)
                     PostQuestionRequest question) {
        try {
            Question questionAdded = questionService.AddQuestion(questionMapper.postQuestionRequestToQuestion(question));
            return ResponseEntity
                    .created(URI.create("/question/add/" + questionAdded.getId()))
                    .body(questionAdded);
        }catch (Exception ex) {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(ex.getMessage());
        }
    }

    @PutMapping("/update")
    @ApiOperation(value = "Update a question",
            notes = "Update a question based on the information received from request")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The Question was successfully updated based on the received request"),
            @ApiResponse(code = 400, message = "Validation error on the received request"),
            @ApiResponse(code = 417, message = "Information provided in the request doesn't not meet the condition.")
    })
    public ResponseEntity<Object> UpdateQuestion(
            @RequestParam
            @ApiParam(name = "userId", value = "User Id", required = true)
                    int userId,
            @RequestParam
            @ApiParam(name = "userDisplayName", value = "User DisplayName", required = true)
                    String userDisplayName,
            @RequestBody
            @ApiParam(name="questionObject", value = "question details", required = true)
                    PutQuestionRequest questionRequest) {
        try {
            Question questionUpdated = questionService.UpdateQuestion(userId, userDisplayName, questionMapper.putQuestionRequestToQuestion(questionRequest));
            return ResponseEntity
                    .ok()
                    .body(questionUpdated);
        }catch(RuntimeException ex) {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(ex.getMessage());
        }
    }

    @PutMapping("/markAsCorrect")
    @ApiOperation(value = "Mark an Answer as correct",
            notes = "Update a question with the correct answer based on the information received from request")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The Question was successfully updated based on the received request"),
            @ApiResponse(code = 400, message = "Validation error on the received request"),
            @ApiResponse(code = 417, message = "Information provided in the request doesn't not meet the condition.")
    })
    public ResponseEntity<String> MarkAnAnswerAsCorrect(
            @RequestParam
            @ApiParam(name = "questionId", value = "QuestionId", required = true)
                    int questionId,
            @RequestParam
            @ApiParam(name = "answerId", value = "Answer Id", required = true)
                    int answerId) {
        try {
            String result = questionService.MarkAnAnswerAsCorrect(questionId,answerId);
            return ResponseEntity
                    .ok()
                    .body(result);
        }catch(Exception ex) {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(ex.getMessage());
        }
    }


    @DeleteMapping("/delete")
    @ApiOperation(value = "Delete a question",
            notes = "Delete a question based on the information received from request")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The Question was successfully deleted based on the received request"),
            @ApiResponse(code = 400, message = "Validation error on the received request"),
            @ApiResponse(code = 417, message = "Information provided in the request doesn't not meet the condition.")
    })
    public ResponseEntity<String> DeleteQuestion(
            @RequestParam
            @ApiParam(name = "id", value = "question Id", required = true)
                    int id) {
        try {
            String message = questionService.DeleteQuestion(id);
            return ResponseEntity
                    .ok()
                    .body(message);
        }catch(Exception ex) {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(ex.getMessage());

        }
    }

    @GetMapping("/all")
    @ApiOperation(value = "Get all questions",
            notes = "Get all questions based on the information received from request")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The Question List was successfully get based on the received request"),
            @ApiResponse(code = 400, message = "Validation error on the received request"),
            @ApiResponse(code = 417, message = "Information provided in the request doesn't not meet the condition.")
    })
    public ResponseEntity<Object> GetQuestions (
            @RequestParam
            @ApiParam(name = "maxNumber", value = "max Number of questions retrieve",required = true)
                    int maxNumber) {
        try {
            List<Question> questionList = questionService.GetQuestions(maxNumber);
            return ResponseEntity
                    .ok()
                    .body(questionList);
        }catch(Exception ex) {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(ex.getMessage());
        }
    }

    @GetMapping("/single")
    @ApiOperation(value = "Get a question including answers and comments for the answers",
            notes = "Get a question expanded based on the information received from request")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The Question was successfully get based on the received request"),
            @ApiResponse(code = 400, message = "Validation error on the received request"),
            @ApiResponse(code = 417, message = "Information provided in the request doesn't not meet the condition.")
    })
    public ResponseEntity<Object> GetExpandedQuestion(
            @RequestParam
            @ApiParam(name = "id", value = "Question Id", required = true)
                    int id) {
        try {
            Question question = questionService.GetQuestionWithAnswers(id);
            return ResponseEntity
                    .ok()
                    .body(question);
        }catch(Exception ex) {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(ex.getMessage());
        }
    }





}
