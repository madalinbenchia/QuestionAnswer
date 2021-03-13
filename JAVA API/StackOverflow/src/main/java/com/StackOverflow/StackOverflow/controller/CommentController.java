package com.StackOverflow.StackOverflow.controller;

import com.StackOverflow.StackOverflow.dto.comments.PostCommentRequest;
import com.StackOverflow.StackOverflow.dto.comments.PutCommentRequest;
import com.StackOverflow.StackOverflow.mapper.CommentMapper;
import com.StackOverflow.StackOverflow.model.Comment;
import com.StackOverflow.StackOverflow.service.AnswerService;
import com.StackOverflow.StackOverflow.service.CommentService;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/comment")
@Api(value = "/comment",
        tags = "Comment Controller")
public class CommentController {

    private final CommentService commentService;
    private final CommentMapper commentMapper;
    private final AnswerService answerService;

    public CommentController(CommentService commentService, CommentMapper commentMapper,AnswerService answerService) {
        this.commentMapper = commentMapper;
        this.commentService = commentService;
        this.answerService = answerService;
    }

    @PostMapping("/add")
    @ApiOperation(value = "Add a comment",
            notes = "Add a comment for an answer based on the information received from request")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "The Comment was successfully added based on the received request"),
            @ApiResponse(code = 400, message = "Validation error on the received request"),
            @ApiResponse(code = 417, message = "Information provided in the request doesn't not meet the condition.")
    })
    public ResponseEntity<Object> AddComment(
            @RequestBody
            @ApiParam(name = "CommentObject", value = "Comment details", required = true)
                    PostCommentRequest commentRequest) {
        try {
            Comment commentAdded = commentService.AddComment(commentMapper.postCommentRequestToComment(commentRequest));
            //update Answer
            int commentCount = commentService.GetCommentCountForAnAswer(commentAdded.getPostId());
            String resultMessage = answerService.UpdateCommentCount(commentAdded.getPostId(),commentCount);

            return ResponseEntity
                    .created(URI.create("/comment/add" + commentAdded.getCommentId()))
                    .body(commentAdded);
        }catch(Exception ex) {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(ex.getMessage());
        }
    }

    @GetMapping("/get")
    @ApiOperation(value = "Get a specific comment",
            notes = "Get a specific comment based on the information received from request")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The Comment was successfully get based on the received request"),
            @ApiResponse(code = 400, message = "Validation error on the received request"),
            @ApiResponse(code = 417, message = "Information provided in the request doesn't not meet the condition.")
    })
    public ResponseEntity<Object> GetComment(
            @RequestParam
            @ApiParam(name = "id", value = "Comment Id", required = true)
                    int id) {
        try {
            Comment comment = commentService.GetCommentById(id);
            return ResponseEntity
                    .ok()
                    .body(comment);
        }catch(Exception ex) {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(ex.getMessage());
        }
    }

    @PutMapping("/update")
    @ApiOperation(value = "Update a specific comment",
            notes = "Update a specific comment for an answer based on the information received from request")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The Comment was successfully updated based on the received request"),
            @ApiResponse(code = 400, message = "Validation error on the received request"),
            @ApiResponse(code = 417, message = "Information provided in the request doesn't not meet the condition.")
    })
    public ResponseEntity<Object> UpdateComment(
            @RequestBody
            @ApiParam(name = "CommentObject", value = "Comment Details", required = true)
                    PutCommentRequest commentRequest) {
        try {
            Comment commentUpdated = commentService.UpdateComment(commentMapper.putCommentRequestToComment(commentRequest));
            return ResponseEntity
                    .ok()
                    .body(commentUpdated);
        }
        catch(Exception ex) {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(ex.getMessage());

        }
    }

    @DeleteMapping("/delete")
    @ApiOperation(value = "Delete a specific comment",
            notes = "Delete a specific comment based on the information received from request")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The Comment was successfully deleted based on the received request"),
            @ApiResponse(code = 400, message = "Validation error on the received request"),
            @ApiResponse(code = 417, message = "Information provided in the request doesn't not meet the condition.")
    })
    public ResponseEntity<String> DeleteComment(
            @RequestParam
            @ApiParam(name = "id", value = "Comment Id", required = true)
                    int id) {
        try {
            String resultMessage = "";
            int postId = answerService.GetAnswerIdByCommmentId(id);
            resultMessage = commentService.DeleteComment(id);
            if(resultMessage.equals("Success")) {
                //update Answer
                int commentCount = commentService.GetCommentCountForAnAswer(postId);
                resultMessage = answerService.UpdateCommentCount(postId,commentCount);
                if(!resultMessage.equals("Success"))
                    resultMessage = "Comment deleted. Can not update Answer Comment Count.";
            }
            return ResponseEntity
                    .ok()
                    .body(resultMessage);
        }catch(Exception ex) {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(ex.getMessage());
        }
    }

    @GetMapping("/all")
    @ApiOperation(value = "Get all comments",
            notes = "Get all comments for an answer based on the information received from request")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The Comments was successfully gets based on the received request"),
            @ApiResponse(code = 400, message = "Validation error on the received request"),
            @ApiResponse(code = 417, message = "Information provided in the request doesn't not meet the condition.")
    })
    public ResponseEntity<Object> GetAllCommentsForAnAswer (
            @RequestParam
            @ApiParam(name = "answerId", value = "Answer Id", required = true)
                    int answerId) {
        try {
            List<Comment> commentList = commentService.GetAllCommentsForAnAswer(answerId);
            return ResponseEntity
                    .ok()
                    .body(commentList);
        }catch(Exception ex) {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(ex.getMessage());
        }
    }
}
