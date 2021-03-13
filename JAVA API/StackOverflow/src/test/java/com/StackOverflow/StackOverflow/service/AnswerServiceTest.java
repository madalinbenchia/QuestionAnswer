package com.StackOverflow.StackOverflow.service;


import com.StackOverflow.StackOverflow.model.Answer;
import com.StackOverflow.StackOverflow.model.Comment;
import com.StackOverflow.StackOverflow.model.User;
import com.StackOverflow.StackOverflow.repository.AnswerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AnswerServiceTest {

    @Mock
    private AnswerRepository answerRepository;
    @Mock
    private UserService userService;
    @InjectMocks
    private AnswerService answerService;

    @Test
    @DisplayName("Add answer")
    public void AddAnswer() {
        User user = new User("Username", "password");
        user.setUserId(1);

        Answer answer = new Answer("Answer body", "answer tags", "Answer title");
        answer.setOwnerUserId(user.getUserId());
        answer.setParentId(1);

        when(userService.GetUserById(answer.getOwnerUserId())).thenReturn(user);
        when(answerRepository.AddAnswer(answer)).thenReturn(answer);

        Answer addAnswer = answerService.AddAnswer(eq(answer.getParentId()),answer);

        assertEquals(addAnswer.getBody(),answer.getBody());
        assertEquals(addAnswer.getTags(), answer.getTags());
        assertEquals(addAnswer.getTitle(), answer.getTitle());

        verify(answerRepository,times(1)).AddAnswer(answer);
    }


    @Test
    @DisplayName("Get Answer By Commment Id")
    public void GetAnswerByCommentId() {
        Answer answer = new Answer("Body answer", "tags answer", "title answer");
        answer.setId(14);
        User user = new User("username", "password");
        user.setUserId(1);
        Comment comment = new Comment(  10,answer.getId(),"Comment text",user.getUserId());

        when(answerRepository.GetAnswerIdByCommentId(comment.getCommentId())).thenReturn(answer.getId());

        int answerId = answerService.GetAnswerIdByCommmentId(comment.getCommentId());

        assertEquals(answerId,answer.getId());

    }

    @Test
    @DisplayName("Get Answers")
    public void GetAnswers() {
        Answer answer = new Answer("Body answer", "tags answer", "title answer");
        answer.setId(14);
        List<Answer> answerList = new ArrayList<>();
        answerList.add(answer);
        int questionId = 5;

        when(answerRepository.GetAnswers(eq(questionId))).thenReturn(answerList);

        List<Answer> answers = answerService.GetAnswers(questionId);

        assertEquals(answers.size(), 1);
        verify(answerRepository,times(1)).GetAnswers(questionId);
    }

    @Test
    @DisplayName("Delete Answer")
    public void DeleteAnswer() {
        int answerId = 14;
        String expectedResult = "Success";

        when(answerRepository.DeleteAnswer(eq(answerId))).thenReturn(expectedResult);

        String result = answerService.DeleteAnswer(answerId);

        assertEquals(result,expectedResult);
        verify(answerRepository,times(1)).DeleteAnswer(answerId);


    }

    @Test
    @DisplayName("Update answer score")
    public void UpdateAnswerScore() {
        int answerId = 14, score = 5;
        String expectedMessageResult = "Success";
        when(answerRepository.UpdateScore(eq(answerId),eq(score))).thenReturn(expectedMessageResult);

        String result = answerService.UpdateScore(answerId,score);

        assertEquals(result,expectedMessageResult);
        verify(answerRepository,times(1)).UpdateScore(answerId,score);
    }

    @Test
    @DisplayName("Update Comment Count")
    public void UpdateCommentCount() {
        int answerId = 14, commentCount = 4;
        String expectedMessageResult = "Success";
        when(answerRepository.UpdateCommentCount(eq(answerId),eq(commentCount))).thenReturn(expectedMessageResult);

        String result = answerService.UpdateCommentCount(answerId,commentCount);

        assertEquals(result,expectedMessageResult);
        verify(answerRepository,times(1)).UpdateCommentCount(answerId,commentCount);
    }
}
