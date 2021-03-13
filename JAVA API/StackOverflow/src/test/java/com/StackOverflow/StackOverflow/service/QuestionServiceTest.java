package com.StackOverflow.StackOverflow.service;

import com.StackOverflow.StackOverflow.model.Question;
import com.StackOverflow.StackOverflow.model.User;
import com.StackOverflow.StackOverflow.repository.QuestionRepository;
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
public class QuestionServiceTest {
    @Mock
    private QuestionRepository questionRepository;
    @Mock
    private UserService userService;
    @InjectMocks
    private QuestionService questionService;

    @Test
    @DisplayName("Add answer")
    public void AddAnswer() {
        User user = new User("Username", "password");
        user.setUserId(1);

        Question question = new Question("Answer body", "answer tags", "Answer title",user.getUserId());
        question.setOwnerUserId(user.getUserId());


        when(userService.GetUserById(question.getOwnerUserId())).thenReturn(user);
        when(questionRepository.AddQuestion(question)).thenReturn(question);

        Question addQuestion = questionService.AddQuestion(question);

        assertEquals(addQuestion.getBody(),question.getBody());
        assertEquals(addQuestion.getTags(), question.getTags());
        assertEquals(addQuestion.getTitle(), question.getTitle());

        verify(questionRepository,times(1)).AddQuestion(question);
    }

    @Test
    @DisplayName("Update Favorite Count")
    public void UpdateFavoriteCount() {
        int questionId = 5, favoriteCount = 5;
        String expectedMessageResult = "Success";
        when(questionRepository.UpdateFavoriteCount(eq(questionId),eq(favoriteCount))).thenReturn(expectedMessageResult);

        String result = questionService.UpdateFavoriteCount(questionId,favoriteCount);

        assertEquals(result,expectedMessageResult);
        verify(questionRepository,times(1)).UpdateFavoriteCount(questionId,favoriteCount);
    }

    @Test
    @DisplayName("Delete Question")
    public void DeleteQuestion() {
        int questiond = 5;
        String expectedResult = "Success";

        when(questionRepository.DeleteQuestion(eq(questiond))).thenReturn(expectedResult);

        String result = questionService.DeleteQuestion(questiond);

        assertEquals(result,expectedResult);
        verify(questionRepository,times(1)).DeleteQuestion(questiond);

    }

    @Test
    @DisplayName("Update Question Total Score")
    public void UpdateQuestionTotalScore() {
        int questionId = 5, totalScore = 10;
        String expectedMessageResult = "Success";
        when(questionRepository.UpdateQuestionScore(eq(questionId),eq(totalScore))).thenReturn(expectedMessageResult);

        String result = questionService.UpdateQuestionTotalScore(questionId,totalScore);

        assertEquals(result,expectedMessageResult);
        verify(questionRepository,times(1)).UpdateQuestionScore(questionId,totalScore);
    }

    @Test
    @DisplayName("Get Questions")
    public void GetQuestions() {
        int maxNumber = 1;
        Question question = new Question("Body answer", "tags answer", "title answer",1);
        question.setId(5);
        List<Question> questionList = new ArrayList<>();
        questionList.add(question);

        when(questionRepository.GetQuestions(eq(maxNumber))).thenReturn(questionList);

        List<Question> questions = questionService.GetQuestions(maxNumber);

        assertEquals(questions.size(), 1);
        verify(questionRepository,times(1)).GetQuestions(maxNumber);
    }
}
