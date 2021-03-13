package com.StackOverflow.StackOverflow.repository;

import com.StackOverflow.StackOverflow.model.Question;
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
public class QuestionRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Question AddQuestion(Question question) {
        final String sqlStatement = "INSERT INTO Posts(AcceptedAnswerId,AnswerCount,Body,ClosedDate,CommentCount,CreationDate,FavoriteCount,LastActivityDate,LastEditDate,LastEditorDisplayName,LastEditorUserId,OwnerUserId,ParentId,PostTypeId,Score, Tags,Title,ViewCount) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatementCreator preparedStatementCreator = (connection) -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1,null);
            preparedStatement.setString(2,Integer.toString(0));
            preparedStatement.setString(3,question.getBody());
            preparedStatement.setString(4,null);
            preparedStatement.setString(5,Integer.toString(0));
            preparedStatement.setString(6,question.getCreationDate());
            preparedStatement.setString(7, Integer.toString(0));
            preparedStatement.setString(8, question.getLastActivityDate());
            preparedStatement.setString(9,null);
            preparedStatement.setString(10,null);
            preparedStatement.setString(11, null);
            preparedStatement.setString(12, Integer.toString(question.getOwnerUserId()));
            preparedStatement.setString(13,Integer.toString(0));
            preparedStatement.setString(14,Integer.toString(question.getPostTypeId()));
            preparedStatement.setString(15,Integer.toString(0));
            preparedStatement.setString(16,question.getTags());
            preparedStatement.setString(17,question.getTitle());
            preparedStatement.setString(18,Integer.toString(0));
            return preparedStatement;
        };

        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(preparedStatementCreator, generatedKeyHolder);
        question.setId(generatedKeyHolder.getKey().intValue());
        return question;
    }

    public Question UpdateQuestion(Question question) {
        final String sqlStatement = "UPDATE Posts SET Title = ?, Body = ?, Tags = ?, LastActivityDate = ?, LastEditDate = ?, LastEditorDisplayName = ?, LastEditorUserId = ? WHERE Id = ?";
        PreparedStatementCreator preparedStatementCreator = (connection) -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setString(1,question.getTitle());
            preparedStatement.setString(2,question.getBody());
            preparedStatement.setString(3,question.getTags());
            preparedStatement.setString(4,question.getLastActivityDate());
            preparedStatement.setString(5, question.getLastEditDate());
            preparedStatement.setString(6,question.getLastEditorDisplayName());
            preparedStatement.setString(7,Integer.toString(question.getLastEditorUserId()));
            preparedStatement.setString(8,Integer.toString(question.getId()));
            return preparedStatement;
        };
        jdbcTemplate.update(preparedStatementCreator);
        return question;
    }

    public String DeleteQuestion(int questionId) {
        final String sqlStatement = "DELETE FROM Posts WHERE Id= ?";
        PreparedStatementCreator preparedStatementCreator = (connection) -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setString(1,Integer.toString(questionId));
            return preparedStatement;
        };
        jdbcTemplate.update(preparedStatementCreator);
        return "Success";
    }

    public List<Question> GetQuestions(int maxNumber) {
        final String sqlStatement = "SELECT * FROM Posts WHERE PostTypeId=1 ORDER BY Score DESC LIMIT " + maxNumber;
        RowMapper<Question> mapper = GetQuestionsMapper();
        return GetQuestionsFromResultSet(jdbcTemplate.query(sqlStatement,mapper));
    }

    public Question GetQuestion(int questionId) {
        final String sqlStatement = "SELECT * FROM Posts WHERE PostTypeId = 1 AND Id = ?";
        RowMapper<Question> mapper = GetQuestionsMapper();
        return GetQuestionFromResultSet(jdbcTemplate.query(sqlStatement,mapper,questionId));
    }

    public String UpdateFavoriteCount(int questionId, int favoriteCount) {
        final String sqlStatement = "UPDATE Posts SET FavoriteCount = ?  WHERE Id =?";
        PreparedStatementCreator preparedStatementCreator = (connection) -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setString(1,Integer.toString(favoriteCount));
            preparedStatement.setString(2,Integer.toString(questionId));
            return preparedStatement;
        };
        jdbcTemplate.update(preparedStatementCreator);
        return "Success";
    }

    public int CalculateTotalScore(int questionId) {
        final String sqlStatement = "Select SUM(Score) FROM Posts Where ParentId = ?";
        return jdbcTemplate.queryForObject(sqlStatement, Integer.class, questionId);
    }

    public int GetQuestionIdBasedOnAnswerId(int answerId) {
        final String sqlStatement = "SELECT ParentId FROM posts WHERE Id = ?";
        return jdbcTemplate.queryForObject(sqlStatement, Integer.class, answerId);
    }

    public String UpdateQuestionScore(int questionId, int score) {
        final String sqlStatement = "UPDATE Posts SET Score = ?  WHERE Id =?";
        PreparedStatementCreator preparedStatementCreator = (connection) -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setString(1,Integer.toString(score));
            preparedStatement.setString(2,Integer.toString(questionId));
            return preparedStatement;
        };
        jdbcTemplate.update(preparedStatementCreator);
        return "Success";
    }

    private List<Question> GetQuestionsFromResultSet(List<Question> questions) {

        if(questions != null && !questions.isEmpty()) {
            return questions;
        } else {
            return null;
        }
    }

    private Question GetQuestionFromResultSet(List<Question> questions) {
        if(questions != null && !questions.isEmpty()) {
            return questions.get(0);
        } else {
            return null;
        }
    }

    public RowMapper<Question> GetQuestionsMapper() {
        return((resultSet, rowNum) ->
                new Question(resultSet.getInt("Id"),
                        resultSet.getInt("AcceptedAnswerId"),
                        resultSet.getInt("AnswerCount"),
                        resultSet.getString("Body"),
                        resultSet.getString("ClosedDate"),
                        resultSet.getInt("CommentCount"),
                        resultSet.getString("CreationDate"),
                        resultSet.getInt("FavoriteCount"),
                        resultSet.getString("LastActivityDate"),
                        resultSet.getString("LastEditDate"),
                        resultSet.getString("LastEditorDisplayName"),
                        resultSet.getInt("LastEditorUserId"),
                        resultSet.getInt("OwnerUserId"),
                        resultSet.getInt("ParentId"),
                        resultSet.getInt("PostTypeId"),
                        resultSet.getInt("Score"),
                        resultSet.getString("Tags"),
                        resultSet.getString("Title"),
                        resultSet.getInt("ViewCount")));
    }



}
