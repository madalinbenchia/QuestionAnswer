package com.StackOverflow.StackOverflow.controller;

import com.StackOverflow.StackOverflow.dto.answers.PostAnswerRequest;
import com.StackOverflow.StackOverflow.dto.answers.PutAnswerRequest;
import com.StackOverflow.StackOverflow.mapper.AnswerMapper;
import com.StackOverflow.StackOverflow.model.Answer;
import com.StackOverflow.StackOverflow.model.Comment;
import com.StackOverflow.StackOverflow.service.AnswerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AnswerController.class)

public class AnswerControllerIT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private AnswerService answerService;
    @MockBean
    private AnswerMapper answerMapper;

    @Test
    public void AddNewAnswer() throws Exception {

        PostAnswerRequest request =new PostAnswerRequest(1,"Test answer", "<Spring>,<Java>","This is the first test answer");
        Answer expectedAnswer = new Answer("Test answer", "<Spring>,<Java>","This is the first test answer");
        int questionId = 1;
        when(answerService.AddAnswer(eq(questionId), any())).thenReturn(expectedAnswer);
        mockMvc.perform(post("/answer/add?questionId="+questionId)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value(request.getTitle()));
    }

    @Test
    public void GetDefaultAnswerWithDefaultComment() throws Exception {
        int answerId = 2;
        Answer expectedAnswer = new Answer("Test answer", "<Spring>,<Java>","This is the first test answer",2);
        Comment comment = new Comment(1,2,"Text Comment",1);
        List<Comment> commentList = new ArrayList<Comment>();
        commentList.add(comment);
        expectedAnswer.setComments(commentList);
        when(answerService.GetAnswerWithComments(answerId)).thenReturn(expectedAnswer);

        mockMvc.perform(get("/answer/singleanswer?answerId="+answerId)
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.comments").isNotEmpty());
    }

    @Test
    public void GetAllDefaultAnswers() throws Exception {
        int questionId = 1;
        List<Answer> answerListExpected = new ArrayList<>();
        answerListExpected.add(new Answer("Test answer", "<Spring>,<Java>","This is the first test answer",2));

        when(answerService.GetAnswers(questionId)).thenReturn(answerListExpected);

        mockMvc.perform(get("/answer/all?questionId="+questionId)
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(1)));
    }

    @Test
    public void DeleteDefaultAnswer() throws Exception {
        int answerId = 2;
        String resultMesssage = "Success";

        when(answerService.DeleteAnswer(answerId)).thenReturn(resultMesssage);

        mockMvc.perform(delete("/answer/delete?id="+answerId)
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Success"));
    }

    @Test
    public void UpdateDefaultAnswer() throws Exception {
        PutAnswerRequest request = new PutAnswerRequest(2,"Test answer Edited", "<Spring>,<Java>","This is the first test answer",1);
        int userId = 1;
        String userDisplayName = "Test Display Name";

        Answer expectedAnswer = new Answer("Test answer Edited", "<Spring>,<Java>","This is the first test answer",2);
        when(answerService.UpdateAnswer(eq(userId),eq(userDisplayName),any())).thenReturn(expectedAnswer);

        mockMvc.perform(put("/answer/update?userId="+userId + "&userDisplayName=" + userDisplayName)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.body").value(expectedAnswer.getBody()));

    }

}
