package com.StackOverflow.StackOverflow.repository;

import com.StackOverflow.StackOverflow.model.Answer;
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
public class AnswerRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Answer AddAnswer(Answer answer) {
        final String sqlStatement = "INSERT INTO Posts(AcceptedAnswerId,AnswerCount,Body,ClosedDate,CommentCount,CreationDate,FavoriteCount,LastActivityDate,LastEditDate,LastEditorDisplayName,LastEditorUserId,OwnerUserId,ParentId,PostTypeId,Score, Tags,Title,ViewCount) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatementCreator preparedStatementCreator = (connection) -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1,null);
            preparedStatement.setString(2,Integer.toString(0));
            preparedStatement.setString(3,answer.getBody());
            preparedStatement.setString(4,null);
            preparedStatement.setString(5,Integer.toString(0));
            preparedStatement.setString(6,answer.getCreationDate());
            preparedStatement.setString(7, Integer.toString(0));
            preparedStatement.setString(8, answer.getLastActivityDate());
            preparedStatement.setString(9,null);
            preparedStatement.setString(10,null);
            preparedStatement.setString(11, null);
            preparedStatement.setString(12, Integer.toString(answer.getOwnerUserId()));
            preparedStatement.setString(13,Integer.toString(answer.getParentId()));
            preparedStatement.setString(14,Integer.toString(answer.getPostTypeId()));
            preparedStatement.setString(15,Integer.toString(0));
            preparedStatement.setString(16,answer.getTags());
            preparedStatement.setString(17,answer.getTitle());
            preparedStatement.setString(18,Integer.toString(0));
            return preparedStatement;
        };

        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(preparedStatementCreator, generatedKeyHolder);
        answer.setId(generatedKeyHolder.getKey().intValue());
        return answer;
    }

    public Answer UpdateAnswer(Answer answer) {
        final String sqlStatement = "UPDATE Posts SET Title = ?, Body = ?, Tags = ?, LastActivityDate = ?, LastEditDate = ?, LastEditorDisplayName = ?, LastEditorUserId = ? WHERE Id = ?";
        PreparedStatementCreator preparedStatementCreator = (connection) -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setString(1,answer.getTitle());
            preparedStatement.setString(2,answer.getBody());
            preparedStatement.setString(3,answer.getTags());
            preparedStatement.setString(4,answer.getLastActivityDate());
            preparedStatement.setString(5, answer.getLastEditDate());
            preparedStatement.setString(6,answer.getLastEditorDisplayName());
            preparedStatement.setString(7,Integer.toString(answer.getLastEditorUserId()));
            preparedStatement.setString(8,Integer.toString(answer.getId()));
            return preparedStatement;
        };
        jdbcTemplate.update(preparedStatementCreator);
        return answer;
    }

    public String MarkAnAswerAsCorrect(int questionId, int answerId) {
        final String sqlStatement = "UPDATE Posts SET AcceptedAnswerId = ? WHERE Id =?";
        PreparedStatementCreator preparedStatementCreator = (connection) -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setString(1,Integer.toString(answerId));
            preparedStatement.setString(2,Integer.toString(questionId));
            return preparedStatement;
        };
        jdbcTemplate.update(preparedStatementCreator);
        return "Success";
    }

    public String UpdateScore(int answerId, int score) {
        final String sqlStatement = "UPDATE Posts SET Score = ?  WHERE Id =?";
        PreparedStatementCreator preparedStatementCreator = (connection) -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setString(1,Integer.toString(score));
            preparedStatement.setString(2,Integer.toString(answerId));
            return preparedStatement;
        };
        jdbcTemplate.update(preparedStatementCreator);
        return "Success";
    }

    public String UpdateCommentCount(int answerId, int commentCount) {
        final String sqlStatement = "UPDATE Posts SET CommentCount = ?  WHERE Id =?";
        PreparedStatementCreator preparedStatementCreator = (connection) -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setString(1,Integer.toString(commentCount));
            preparedStatement.setString(2,Integer.toString(answerId));
            return preparedStatement;
        };
        jdbcTemplate.update(preparedStatementCreator);
        return "Success";
    }

    public Answer GetAnswer(int answerId) {
        final String sqlStatement = "SELECT * FROM Posts WHERE PostTypeId = 2 AND Id = ?";
        RowMapper<Answer> mapper = GetAnswersMapper();
        return GetAnswerFromResultSet(jdbcTemplate.query(sqlStatement,mapper,answerId));
    }

    public int GetAnswerIdByCommentId(int commentId) {
        final String sqlStatement = "SELECT PostId FROM comments WHERE CommentId = ?";
        int result = jdbcTemplate.queryForObject(sqlStatement, Integer.class, commentId);
        return result;
    }


    public int GetAllVotesForPosts(int postId, int voteTypeId) {
        final String sqlStatement = "SELECT COUNT(*) FROM votes WHERE PostId = ? AND VoteTypeId = ?";
        return jdbcTemplate.queryForObject(sqlStatement, Integer.class, new Object[] {postId, voteTypeId});
    }

    public String DeleteAnswer(int id) {
        final String sqlStatement = "DELETE FROM Posts WHERE Id = ?";
        PreparedStatementCreator preparedStatementCreator = (connection) -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setString(1,Integer.toString(id));
            return preparedStatement;
        };
        jdbcTemplate.update(preparedStatementCreator);
        return "Success";
    }

    public List<Answer> GetAnswers(int questionId) {
        final String sqlStatement = "SELECT * FROM Posts WHERE PostTypeId=2 AND ParentId = ? ORDER BY Score DESC";
        RowMapper<Answer> mapper = GetAnswersMapper();
        return GetAnswersFromResultSet(jdbcTemplate.query(sqlStatement,mapper, questionId));
    }

    private List<Answer> GetAnswersFromResultSet(List<Answer> answers) {

        if(answers != null && !answers.isEmpty()) {
            return answers;
        } else {
            return null;
        }
    }

    private Answer GetAnswerFromResultSet(List<Answer> answers) {
        if(answers != null && !answers.isEmpty()) {
            return answers.get(0);
        } else {
            return null;
        }
    }
    public RowMapper<Answer> GetAnswersMapper() {
        return((resultSet, rowNum) ->
                new Answer(resultSet.getInt("Id"),
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
