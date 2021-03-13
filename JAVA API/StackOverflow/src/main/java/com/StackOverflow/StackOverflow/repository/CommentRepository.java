package com.StackOverflow.StackOverflow.repository;

import com.StackOverflow.StackOverflow.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class CommentRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Comment AddComment(Comment comment) {
        final String sqlStatement = "INSERT INTO comments(CreationDate,PostId,Text,UserId) VALUES (?,?,?,?)";
        PreparedStatementCreator preparedStatementCreator = (connection) -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1,comment.getCreationDate());
            preparedStatement.setString(2,Integer.toString(comment.getPostId()));
            preparedStatement.setString(3,comment.getText());
            preparedStatement.setString(4,Integer.toString(comment.getUserId()));
            return preparedStatement;
        };
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(preparedStatementCreator, generatedKeyHolder);
        comment.setCommentId(generatedKeyHolder.getKey().intValue());
        return comment;

    }

    public Comment UpdateComment(Comment comment) {
        final String sqlStatement = "UPDATE comments SET Text = ? WHERE CommentId = ?";
        PreparedStatementCreator preparedStatementCreator = (connection) -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setString(1,comment.getText());
            preparedStatement.setString(2,Integer.toString(comment.getCommentId()));
            return preparedStatement;
        };
        jdbcTemplate.update(preparedStatementCreator);
        return comment;
    }

    public String DeleteComment(int commentId) {
        final String sqlStatement = "DELETE FROM comments WHERE CommentId= ?";
        PreparedStatementCreator preparedStatementCreator = (connection) -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setString(1,Integer.toString(commentId));
            return preparedStatement;
        };
        jdbcTemplate.update(preparedStatementCreator);
        return "Success";
    }

    public int GetCommentCountForAnAswer(int answerId) {
        final String sqlStatement = "SELECT COUNT(*) FROM comments WHERE PostId=?";
        return jdbcTemplate.queryForObject(sqlStatement,Integer.class,answerId);
    }

    public Comment GetCommentById(int commentId) {
        final String sqlStatement = "SELECT * FROM comments WHERE CommentId = ?";
        RowMapper<Comment> mapper = GetCommmentsMapper();
        return GetCommentFromResultSet(jdbcTemplate.query(sqlStatement,mapper,commentId));
    }

    public List<Comment> GetCommentsForAnAnswer(int answerId) {
        final String sqlStatement = "SELECT * FROM comments WHERE PostId = ?";
        RowMapper<Comment> mapper = GetCommmentsMapper();
        return GetCommentsFromResultSet(jdbcTemplate.query(sqlStatement,mapper,answerId));
    }

    private Comment GetCommentFromResultSet(List<Comment> comments) {
        if(comments != null && !comments.isEmpty()) {
            return comments.get(0);
        } else {
            return null;
        }
    }

    private List<Comment> GetCommentsFromResultSet(List<Comment> comments) {
        if(comments != null && !comments.isEmpty()) {
            return comments;
        } else {
            return null;
        }
    }
    public RowMapper<Comment> GetCommmentsMapper() {
        return((resultSet, rowNum) ->
                new Comment(resultSet.getInt("CommentId"),
                        resultSet.getString("CreationDate"),
                        resultSet.getInt("PostId"),
                        resultSet.getString("Text"),
                        resultSet.getInt("UserId")));
    }



}
