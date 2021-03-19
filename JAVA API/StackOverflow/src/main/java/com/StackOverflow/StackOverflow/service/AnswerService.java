package com.StackOverflow.StackOverflow.service;

import com.StackOverflow.StackOverflow.exception.SqlException;
import com.StackOverflow.StackOverflow.exception.user.UserNotFoundException;
import com.StackOverflow.StackOverflow.exception.post.AnswerNotFoundException;
import com.StackOverflow.StackOverflow.exception.post.EmptyQuestionListException;
import com.StackOverflow.StackOverflow.exception.post.QuestionNotFoundException;
import com.StackOverflow.StackOverflow.exception.post.StringNullOrEmptyException;
import com.StackOverflow.StackOverflow.model.Answer;
import com.StackOverflow.StackOverflow.model.User;
import com.StackOverflow.StackOverflow.repository.AccountRepository;
import com.StackOverflow.StackOverflow.repository.AnswerRepository;
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
public class AnswerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountRepository.class);

    private final AnswerRepository answerRepository;
    private final UserService userService;
    private final CommentService commentService;


    public AnswerService(AnswerRepository answerRepository, UserService userService, CommentService commentService) {
        this.answerRepository = answerRepository;
        this.userService = userService;
        this.commentService = commentService;
    }

    @Transactional
    public Answer AddAnswer(int questionId, Answer answer) {
        //Validate mandatory fields
        ValidateAnswerObject(answer);
        try {
            Date date = Calendar.getInstance().getTime();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String strDate = dateFormat.format(date);

            answer.setPostTypeId(2);
            answer.setParentId(questionId);
            answer.setLastActivityDate(strDate);
            answer.setCreationDate(strDate);
            Answer answerAdded = answerRepository.AddAnswer(answer);
            return answerAdded;
        }catch(Exception ex) {
            LOGGER.error(ex.getMessage());
            throw new SqlException(ex.getMessage());
        }
    }

    @Transactional
    public Answer UpdateAnswer(int userId, String userDisplayName, Answer answer) {
        //Validate mandatory question fields
        ValidateAnswerObject(answer);

        //validate users information
        try {
            User user = userService.GetUserById(userId);
            if(!user.getDisplayName().equals(userDisplayName))
                throw new UserNotFoundException(userId, "UserId and User Display Name provided doesn't match.");
        }catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            throw new UserNotFoundException(answer.getOwnerUserId(), ex.getMessage());
        }

        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String strDate = dateFormat.format(date);
        answer.setLastActivityDate(strDate);
        answer.setLastEditDate(strDate);
        answer.setLastEditorDisplayName(userDisplayName);
        answer.setLastEditorUserId(userId);
        //Update the question
        try {
            Answer finalAnswer =  answerRepository.UpdateAnswer(answer);
            finalAnswer.setPostTypeId(2);
            return finalAnswer;
        }catch (SqlException ex) {
            LOGGER.error(ex.getMessage());
            throw new RuntimeException(ex.getMessage());
        }
    }

    public int GetAnswerIdByCommmentId(int commentId) {
        try{
            return answerRepository.GetAnswerIdByCommentId(commentId);
        }catch(SqlException ex) {
            LOGGER.error(ex.getMessage());
            throw new SqlException(ex.getMessage());
        }
    }

    @Transactional
    public String DeleteAnswer(int id) {
        try {
            return answerRepository.DeleteAnswer(id);
        }catch(SqlException ex) {
            LOGGER.error(ex.getMessage());
            throw new SqlException(ex.getMessage());
        }
    }

    public List<Answer> GetAnswers(int questionId) {
        try {

            List<Answer> result = answerRepository.GetAnswers(questionId);
            //if(!result.equals(null))
            return result;
            //else
                //throw new EmptyQuestionListException();
        }
        catch(Exception ex) {
            LOGGER.error(ex.getMessage());
            throw  new QuestionNotFoundException(); 
        }

    }

    public List<Answer> GetAnswersWithComments(int questionId) {
        try {

            List<Answer> result = answerRepository.GetAnswers(questionId);
            //if(!result.equals(null)) {
                for (Answer answer : result) {
                    answer.setComments(commentService.GetAllCommentsForAnAswer(answer.getId()));
                }
                return result;
            //}

            //else
                //throw new EmptyQuestionListException();
        }
        catch(Exception ex) {
            LOGGER.error(ex.getMessage());
            throw  new QuestionNotFoundException();
        }
    }

    public Answer GetAnswer(int questionId) {
        try {
            return answerRepository.GetAnswer(questionId);
        }catch(SqlException ex) {
            LOGGER.error(ex.getMessage());
            return null;
        }
    }

    public Answer GetAnswerWithComments(int answerId) {
        Answer answerResult = null;
        try {
            answerResult = answerRepository.GetAnswer(answerId);
        }catch(Exception ex) {
            LOGGER.error(ex.getMessage());
            throw new AnswerNotFoundException(answerId);
        }
        if(answerResult != null) {
            try {
                answerResult.setComments(commentService.GetAllCommentsForAnAswer(answerId));
                return answerResult;
            }catch(Exception ex) {
                LOGGER.error(ex.getMessage());
                throw new SqlException(ex.getMessage());
            }
        }
        return null;
    }

    public String MarkAnAswerAsCorrect(int questionId, int answerId) {

        //questionId and answerId are valid
        try {
            return answerRepository.MarkAnAswerAsCorrect(questionId,answerId);
        }catch(SqlException ex) {
            LOGGER.error(ex.getMessage());
            throw  new SqlException(ex.getMessage());
        }
    }

    @Transactional
    public String UpdateScore(int answerId, int score) {
        try {
            return answerRepository.UpdateScore(answerId,score);
        }catch(Exception ex) {
            LOGGER.error(ex.getMessage());
            throw new SqlException(ex.getMessage());
        }
    }

    @Transactional
    public String UpdateCommentCount(int answerId, int commentCount) {
        try {
            return answerRepository.UpdateCommentCount(answerId,commentCount);
        }catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            throw new RuntimeException(ex.getMessage());
        }
    }

    public void ValidateAnswerObject(Answer answer) {
        //Validate mandatory fields
        if(answer.getTitle().isEmpty() || answer.getTitle().equals(null))
            throw new StringNullOrEmptyException("Title can not be null or empty.");

        if(answer.getBody().isEmpty() || answer.getBody().equals(null))
            throw new StringNullOrEmptyException("Question body can not be null or empty.");

        try {
            User user = userService.GetUserById(answer.getOwnerUserId());

        }catch (Exception ex) {
            throw new UserNotFoundException(answer.getOwnerUserId(), ex.getMessage());
        }
    }



}
