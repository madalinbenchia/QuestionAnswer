package com.StackOverflow.StackOverflow.controller;

import com.StackOverflow.StackOverflow.dto.answers.PostAnswerRequest;
import com.StackOverflow.StackOverflow.dto.answers.PutAnswerRequest;
import com.StackOverflow.StackOverflow.exception.SqlException;
import com.StackOverflow.StackOverflow.mapper.AnswerMapper;
import com.StackOverflow.StackOverflow.model.Answer;
import com.StackOverflow.StackOverflow.service.AnswerService;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/answer")
@Api(value = "/answer",
        tags = "Answer Controller")
public class AnswerController {

    private final AnswerService answerService;
    private final AnswerMapper answerMapper;

    public AnswerController(AnswerService answerService, AnswerMapper answerMapper) {
        this.answerMapper = answerMapper;
        this.answerService = answerService;
    }

    @PostMapping("/add")
    @ApiOperation(value = "Create an answer for a question",
            notes = "Create an answer for a question based on the information received from request")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "The Answer was successfully created based on the received request"),
            @ApiResponse(code = 400, message = "Validation error on the received request"),
            @ApiResponse(code = 417, message = "Information provided in the request doesn't not meet the condition.")
    })
    public ResponseEntity<Object> AddAnswer(

            @RequestParam
            @ApiParam(name = "questionId", value = "Question Id", required = true)
                    int questionId,
            @RequestBody
            @ApiParam(name = "Answer", value = "Answer details", required = true)
                    PostAnswerRequest answerRequest) {
        try {
            Answer answerAdded = answerService.AddAnswer(questionId, answerMapper.postAnswerRequestToAnswer(answerRequest));
            return ResponseEntity
                    .created(URI.create("/answer/add/" + answerAdded.getId()))
                    .body(answerAdded);
        }catch (Exception ex) {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(ex.getMessage());
        }
    }


    @GetMapping("/all")
    @ApiOperation(value = "Get all answers for a question",
            notes = "Get all answers for a question based on the information received from request")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "The Answers was successfully get based on the received request"),
            @ApiResponse(code = 400, message = "Validation error on the received request"),
            @ApiResponse(code = 417, message = "Information provided in the request doesn't not meet the condition.")
    })
    public ResponseEntity<Object> GetAnswers(
            @RequestParam
            @ApiParam(name = "questionId", value = "Question Id", required = true)
                    int questionId) {
        try {
            List<Answer> answersResult = answerService.GetAnswers(questionId);
            return ResponseEntity
                    .ok()
                    .body(answersResult);
        }catch(Exception ex) {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(ex.getMessage());
        }
    }

    @GetMapping("/singleanswer")
    @ApiOperation(value = "Get a specific answer",
            notes = "Get a specific answer based on the information received from request")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "The Answer was successfully get based on the received request"),
            @ApiResponse(code = 400, message = "Validation error on the received request"),
            @ApiResponse(code = 417, message = "Information provided in the request doesn't not meet the condition.")
    })
    public ResponseEntity<Object> GetAnswerWithhComments(
            @RequestParam
            @ApiParam(name = "answerId", value = "Answer Id", required = true)
                    int answerId) {
        try {
            Answer answerResult = answerService.GetAnswerWithComments(answerId);
            return ResponseEntity
                    .ok()
                    .body(answerResult);
        }catch(Exception ex) {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(ex.getMessage());
        }
    }

    @DeleteMapping("/delete")
    @ApiOperation(value = "Delete an answer",
            notes = "Delete a specific answer based on the information received from request")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "The Answer was successfully deleted based on the received request"),
            @ApiResponse(code = 400, message = "Validation error on the received request"),
            @ApiResponse(code = 417, message = "Information provided in the request doesn't not meet the condition.")
    })
    public ResponseEntity<String> DeleteAnswer(
            @RequestParam
            @ApiParam(name = "id", value = "Answer Id", required = true)
                    int id) {
        try {
            String result = answerService.DeleteAnswer(id);
            if(result.equals("Success"))
                return ResponseEntity
                        .ok()
                        .body(result);
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(result);
        }catch(SqlException ex) {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(ex.getMessage());
        }
    }



    @PutMapping("/update")
    @ApiOperation(value = "Update a specific answer",
            notes = "Update a specific answer based on the information received from request")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "The Answer was successfully updated based on the received request"),
            @ApiResponse(code = 400, message = "Validation error on the received request"),
            @ApiResponse(code = 417, message = "Information provided in the request doesn't not meet the condition.")
    })
    public ResponseEntity<Object> UpdateAnswer (
            @RequestParam
            @ApiParam(name = "userId", value = "User Id", required = true)
                    int userId,
            @RequestParam
            @ApiParam(name = "userDisplayName", value = "User Display Name", required = true)
                    String userDisplayName,
            @RequestBody
            @ApiParam(name = "answerRequest", value = "Answer Details", required = true)
                    PutAnswerRequest answerRequest) {
        try {
            Answer answerUpdated = answerService.UpdateAnswer(userId,userDisplayName, answerMapper.putAnswerRequestToAnswer(answerRequest));
            return ResponseEntity
                    .ok()
                    .body(answerUpdated);
        }catch(Exception ex) {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(ex.getMessage());
        }
    }



}
