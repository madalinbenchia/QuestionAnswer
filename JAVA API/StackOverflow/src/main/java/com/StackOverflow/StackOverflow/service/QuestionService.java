package com.StackOverflow.StackOverflow.service;

import com.StackOverflow.StackOverflow.exception.SqlException;
import com.StackOverflow.StackOverflow.exception.user.UserNotFoundException;
import com.StackOverflow.StackOverflow.exception.post.*;
import com.StackOverflow.StackOverflow.model.Answer;
import com.StackOverflow.StackOverflow.model.Question;
import com.StackOverflow.StackOverflow.model.User;
import com.StackOverflow.StackOverflow.repository.AccountRepository;
import com.StackOverflow.StackOverflow.repository.QuestionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class QuestionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountRepository.class);

    private final QuestionRepository questionRepository;
    private final UserService userService;
    private final AnswerService answerService;

    public QuestionService(QuestionRepository questionRepository, UserService userService, AnswerService answerService) {
        this.questionRepository = questionRepository;
        this.userService = userService;
        this.answerService = answerService;
    }


    @Transactional
    public Question AddQuestion(Question question) {

        //Validate mandatory fields
        ValidateQuestionObject(question);


        try {
            question.setPostTypeId(1);
            Question questionAdded = questionRepository.AddQuestion(question);
            return questionAdded;
        }catch(Exception ex) {
            LOGGER.error(ex.getMessage());
            throw new SqlException(ex.getMessage());
        }
    }

    @Transactional
    public Question UpdateQuestion(int userId, String userDisplayName, Question question) {
        //Validate mandatory question fields
        ValidateQuestionObject(question);

        //validate users information
        try {
            User user = userService.GetUserById(userId);
            if(!user.getDisplayName().equals(userDisplayName))
                throw new UserNotFoundException(userId, "UserId and User Display Name provided doesn't match.");
        }catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            throw new UserNotFoundException(question.getOwnerUserId(), ex.getMessage());
        }

        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String strDate = dateFormat.format(date);
        question.setLastActivityDate(strDate);
        question.setLastEditDate(strDate);
        question.setLastEditorDisplayName(userDisplayName);
        question.setLastEditorUserId(userId);
        //Update the question
        try {
            return questionRepository.UpdateQuestion(question);
        }catch (SqlException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    @Transactional
    public String UpdateFavoriteCount(int questiondId, int favoriteCount) {
        try {
            return questionRepository.UpdateFavoriteCount(questiondId,favoriteCount);
        }catch(SqlException ex) {
            LOGGER.error(ex.getMessage());
            throw new SqlException(ex.getMessage());
        }
    }

    @Transactional
    public String DeleteQuestion(int id) {
        try {
            return questionRepository.DeleteQuestion(id);
        }catch(Exception ex) {
            LOGGER.error(ex.getMessage());
            throw new RuntimeException(ex.getMessage());
        }
    }

    public List<Question> GetQuestions(int maxNumber) {
        //if(maxNumber > 0) {
            List<Question> result = questionRepository.GetQuestions(maxNumber);
            if(!result.equals(null))
                return result;
            else
                throw new EmptyQuestionListException();
        //}
        //else
            ///throw  new NegativeMaxNumberException();
    }

    public Question GetQuestionWithAnswers(int id) {
    	Question question = new Question();
        try {
            question = questionRepository.GetQuestion(id);
            question.setAnswers(answerService.GetAnswersWithComments(id));
            return question;
        }catch(Exception ex) {
            LOGGER.error(ex.getMessage());
            return question;
        }
    }

    public Question GetQuestion(int id) {
        try {
            return questionRepository.GetQuestion(id);
        }catch(Exception ex) {
            LOGGER.error(ex.getMessage());
            return null;
        }
    }

    public int GetQuestionIdBasedOnAnswerId(int answerId) {
        return questionRepository.GetQuestionIdBasedOnAnswerId(answerId);
    }

    public int CalculateQuestionTotalScore(int questionId) {
        return questionRepository.CalculateTotalScore(questionId);
    }

    @Transactional
    public String UpdateQuestionTotalScore(int questionId, int score) {
        try {
            return questionRepository.UpdateQuestionScore(questionId,score);
        }catch(Exception ex) {
            throw new SqlException(ex.getMessage());
        }
    }

    public void ValidateQuestionObject(Question question) {
        //Validate mandatory fields
        if(question.getTitle().isEmpty() || question.getTitle().equals(null))
            throw new StringNullOrEmptyException("Title can not be null or empty.");

        if(question.getBody().isEmpty() || question.getBody().equals(null))
            throw new StringNullOrEmptyException("Question body can not be null or empty.");

        try {
            User user = userService.GetUserById(question.getOwnerUserId());

        }catch (Exception ex) {
            throw new UserNotFoundException(question.getOwnerUserId(), ex.getMessage());
        }
    }

    @Transactional
    public String MarkAnAnswerAsCorrect(int questionId, int answerId) {
        //check if questionId
        try {
            Question questionFounded = questionRepository.GetQuestion(questionId);
            if(questionFounded == null)
                throw new QuestionNotFoundException();
        }catch(Exception ex) {
            LOGGER.error(ex.getMessage());
            throw new SqlException(ex.getMessage());
        }

        //check if answerId exist
        try {
            Answer answerFounded = answerService.GetAnswer(answerId);
            if(answerFounded == null)
                throw new AnswerNotFoundException(answerId);
        }catch(Exception ex) {
            LOGGER.error(ex.getMessage());
            throw new SqlException(ex.getMessage());
        }

        //questionId and answerId are valid
        try {
            return answerService.MarkAnAswerAsCorrect(questionId,answerId);
        }catch(SqlException ex) {
            LOGGER.error(ex.getMessage());
            throw  new SqlException(ex.getMessage());
        }
    }
}
