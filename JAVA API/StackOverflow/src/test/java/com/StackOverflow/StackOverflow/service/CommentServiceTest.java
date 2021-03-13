package com.StackOverflow.StackOverflow.service;

import com.StackOverflow.StackOverflow.model.Answer;
import com.StackOverflow.StackOverflow.model.Comment;
import com.StackOverflow.StackOverflow.model.User;
import com.StackOverflow.StackOverflow.repository.CommentRepository;
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
public class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;
    @Mock
    private UserService userService;
    @InjectMocks
    private CommentService commentService;


    @Test
    @DisplayName("Add comment")
    public void AddComment() {

        User user = new User("Username", "password");
        user.setUserId(1);
        Answer answer = new Answer("Text body","Tags","Title");
        answer.setId(14);
        Comment comment = new Comment(answer.getId(),"Comment text",user.getUserId());

        when(userService.GetUserById(user.getUserId())).thenReturn(user);
        when(commentRepository.AddComment(comment)).thenReturn(comment);

        Comment comment1 = commentService.AddComment(comment);

        assertEquals(comment.getPostId(),comment1.getPostId());
        assertEquals(comment.getText(),comment1.getText());
        assertEquals(comment.getUserId(), comment1.getUserId());
        verify(commentRepository,times(1)).AddComment(comment);
    }

    @Test
    @DisplayName("Update comment")
    public void UpdateComment() {
        User user = new User("Username", "password");
        user.setUserId(1);
        Answer answer = new Answer("Text body","Tags","Title");
        answer.setId(14);
        Comment comment = new Comment(  10,answer.getId(),"Comment text",user.getUserId());


        when(userService.GetUserById(user.getUserId())).thenReturn(user);
        when(commentRepository.UpdateComment(comment)).thenReturn(comment);

        Comment comment1 = commentService.UpdateComment(comment);

        assertEquals(comment.getText(),comment1.getText());

        verify(commentRepository,times(1)).UpdateComment(comment);
    }

    @Test
    @DisplayName("Get all comment for an answer")
    public void GetComments() {
        Comment comment = new Comment(10,14,"Comment test", 1);
        List<Comment> commentList = new ArrayList<>();
        commentList.add(comment);
        int answerId = 14;
        when(commentRepository.GetCommentsForAnAnswer(14)).thenReturn(commentList);

        List<Comment> comments = commentService.GetAllCommentsForAnAswer(answerId);

        assertEquals(comments.size(), 1);
        verify(commentRepository,times(1)).GetCommentsForAnAnswer(answerId);
    }

}
