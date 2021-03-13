package com.StackOverflow.StackOverflow.controller;

import com.StackOverflow.StackOverflow.dto.comments.PostCommentRequest;
import com.StackOverflow.StackOverflow.dto.comments.PutCommentRequest;
import com.StackOverflow.StackOverflow.mapper.CommentMapper;
import com.StackOverflow.StackOverflow.model.Comment;
import com.StackOverflow.StackOverflow.service.AnswerService;
import com.StackOverflow.StackOverflow.service.CommentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CommentController.class)
public class CommentControllerIT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private CommentService commentService;
    @MockBean
    private CommentMapper commentMapper;
    @MockBean
    private AnswerService answerService;

    @Test
    public void AddNewComment() throws Exception {

        PostCommentRequest request = new PostCommentRequest(2,"Second comment test",1);
        Comment expectedComment = new Comment(2,"Second comment test", 1);

        when(commentService.AddComment(any())).thenReturn(expectedComment);
        mockMvc.perform(post("/comment/add")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.text").value(request.getText()));
    }

    @Test
    public void GetDefaultComment() throws Exception {
        int commentId = 1;
        Comment expectedComment = new Comment(1,2,"Text Comment",1);

        when(commentService.GetCommentById(commentId)).thenReturn(expectedComment);
        mockMvc.perform(get("/comment/get?id=" + commentId)
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text").value(expectedComment.getText()));

    }

    @Test
    public void GetAllDefaultComment() throws  Exception {
        int answerId = 2;
        Comment comment = new Comment(1,2,"Text Comment",1);
        List<Comment> expectedComments = new ArrayList<>();
        expectedComments.add(comment);

        when(commentService.GetAllCommentsForAnAswer(answerId)).thenReturn(expectedComments);
        mockMvc.perform(get("/comment/all?answerId=" + answerId)
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(1)));
    }

    @Test
    public void UpdateDefaultComment() throws Exception {
        PutCommentRequest request = new PutCommentRequest(1,2,"Edited Comment",1);
        Comment expectedComment = new Comment(1,2,"Edited Comment", 1);

        when(commentService.UpdateComment(any())).thenReturn(expectedComment);
        mockMvc.perform(put("/comment/update")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text").value(request.getText()));
    }




}
