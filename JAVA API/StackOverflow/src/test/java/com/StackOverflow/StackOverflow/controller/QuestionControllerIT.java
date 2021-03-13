package com.StackOverflow.StackOverflow.controller;

import com.StackOverflow.StackOverflow.dto.question.PostQuestionRequest;
import com.StackOverflow.StackOverflow.dto.question.PutQuestionRequest;
import com.StackOverflow.StackOverflow.mapper.QuestionMapper;
import com.StackOverflow.StackOverflow.model.Answer;
import com.StackOverflow.StackOverflow.model.Comment;
import com.StackOverflow.StackOverflow.model.Question;
import com.StackOverflow.StackOverflow.service.QuestionService;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = QuestionController.class)

public class QuestionControllerIT {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private QuestionService questionService;
    @MockBean
    private QuestionMapper questionMapper;

    @Test
    public void AddNewQuestion() throws Exception {

        PostQuestionRequest request =new PostQuestionRequest("Test answer", "<Spring>,<Java>","This is the first test answer",1);
        Question questionExpected = new Question("Test answer", "<Spring>,<Java>","This is the first test answer",1);

        when(questionService.AddQuestion(any())).thenReturn(questionExpected);

        mockMvc.perform(post("/question/add")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value(questionExpected.getTitle()));
    }

    @Test
    public void UpdateDefaultQuestion() throws Exception {
        int userId = 1;
        String userDisplayName = "Test Display Name";
        PutQuestionRequest request = new PutQuestionRequest(1,"Test answer Edited", "<Spring>,<Java>","This is the first test answer",userId);
        Question questionExpected = new Question(1,"Test answer Edited", "<Spring>,<Java>","This is the first test answer",userId);

        when(questionService.UpdateQuestion(eq(userId),eq(userDisplayName),any())).thenReturn(questionExpected);

        mockMvc.perform(put("/question/update?userId=" + userId + "&userDisplayName=" + userDisplayName)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.body").value(questionExpected.getBody()));

    }

    @Test
    public void MarkAnAnswerAsCorrect() throws Exception {
        int questionId = 1;
        int answerId = 2;

        when(questionService.MarkAnAnswerAsCorrect(eq(questionId),eq(answerId))).thenReturn("Success");

        mockMvc.perform(put("/question/markAsCorrect?questionId=" + questionId + "&answerId=" + answerId)
                .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    public void GetAllDefaultQuestion() throws Exception {
        int maxNumber = 1;
        List<Question> questionListExpected = new ArrayList<>();
        questionListExpected.add(new Question(1,"Test question body", "Test tag","Test Question Title",1));

        when(questionService.GetQuestions(maxNumber)).thenReturn(questionListExpected);

        mockMvc.perform(get("/question/all?maxNumber="+maxNumber)
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(1)));
    }

    @Test
    public void GetExpandedDefaultQuestion() throws Exception {
        int questionId = 1;
        Question questionExpected = new Question(1,"Test question body", "Test tag","Test Question Title",1);
        Answer answer = new Answer(2,"Test answer", "<Spring>,<Java>","This is the first test answer",1);
        Comment comment = new Comment(1,2,"Text Comment",1);
        List<Comment> commentList = new ArrayList<>();
        commentList.add(comment);
        answer.setComments(commentList);

        List<Answer> answerList = new ArrayList<>();
        answerList.add(answer);
        questionExpected.setAnswers(answerList);

        when(questionService.GetQuestionWithAnswers(questionId)).thenReturn(questionExpected);

        mockMvc.perform(get("/question/single?id="+questionId)
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.answers",hasSize(1)))
                .andExpect(jsonPath("$.answers[0].comments", hasSize(1)));

    }

    @Test
    public void DeleteDefaultQuestion() throws Exception {
        int questionId = 1;
        String expectedResultMessage = "Success";

        when(questionService.DeleteQuestion(questionId)).thenReturn(expectedResultMessage);

        mockMvc.perform(delete("/question/delete?id="+questionId)
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(expectedResultMessage));
    }

}
