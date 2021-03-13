package com.StackOverflow.StackOverflow.service;

import com.StackOverflow.StackOverflow.exception.SqlException;
import com.StackOverflow.StackOverflow.exception.user.UserNotFoundException;
import com.StackOverflow.StackOverflow.exception.comment.CommentBodyTooLongException;
import com.StackOverflow.StackOverflow.exception.comment.CommentNotFoundException;
import com.StackOverflow.StackOverflow.model.Comment;
import com.StackOverflow.StackOverflow.model.User;
import com.StackOverflow.StackOverflow.repository.AccountRepository;
import com.StackOverflow.StackOverflow.repository.CommentRepository;
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
public class CommentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountRepository.class);

    private final CommentRepository commentRepository;
    private final UserService userService;

    public CommentService(CommentRepository commentRepository, UserService userService) {
        this.userService = userService;
        this.commentRepository = commentRepository;
    }

    @Transactional
    public Comment AddComment(Comment comment) {
        try {
            ValidateCommentFields(comment);
            Date date = Calendar.getInstance().getTime();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String strDate = dateFormat.format(date);
            comment.setCreationDate(strDate);
            Comment commentAdded = commentRepository.AddComment(comment);
            return commentAdded;
        }catch(Exception ex) {
            LOGGER.error(ex.getMessage());
            throw new SqlException(ex.getMessage());
        }
    }

    public Comment GetCommentById(int id) {
        try {
            return commentRepository.GetCommentById(id);
        }catch(SqlException ex) {
            LOGGER.error(ex.getMessage());
            throw new CommentNotFoundException(id);
        }
    }

    public List<Comment> GetAllCommentsForAnAswer(int answerId) {
        try {
            return commentRepository.GetCommentsForAnAnswer(answerId);
        }catch(Exception ex) {
            LOGGER.error(ex.getMessage());
            throw  new SqlException(ex.getMessage());
        }
    }

    @Transactional
    public Comment UpdateComment(Comment comment) {
        try {
            ValidateCommentFields(comment);
            return commentRepository.UpdateComment(comment);

        }catch(Exception ex) {
            LOGGER.error(ex.getMessage());
            throw new RuntimeException((ex.getMessage()));
        }
    }

    @Transactional
    public String DeleteComment(int commentId) {
        try {
            return commentRepository.DeleteComment(commentId);
        }catch(SqlException ex) {
            LOGGER.error(ex.getMessage());
            throw new CommentNotFoundException(commentId);
        }
    }

    public void ValidateCommentFields(Comment comment) {
        User user = userService.GetUserById(comment.getUserId());
        if(user == null)
            throw new UserNotFoundException(comment.getUserId(), "");
        if(comment.getText().length() > 1000)
            throw new CommentBodyTooLongException();
    }

    public int GetCommentCountForAnAswer(int answerId) {
        try {
            return commentRepository.GetCommentCountForAnAswer(answerId);
        }catch(Exception ex) {
            LOGGER.error(ex.getMessage());
            return 0;
        }
    }
}
