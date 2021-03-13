package com.StackOverflow.StackOverflow.repository;

import com.StackOverflow.StackOverflow.model.Vote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;

@Repository
public class VoteRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public String AddVote(Vote vote) {
        final String sqlStatement = "INSERT INTO votes(PostId,UserId,VoteTypeId,CreationDate) VALUES (?,?,?,?)";
        PreparedStatementCreator preparedStatementCreator = (connection) -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, Integer.toString(vote.getPostId()));
            preparedStatement.setString(2, Integer.toString(vote.getUserId()));
            preparedStatement.setString(3,Integer.toString(vote.getVoteTypeId()));
            preparedStatement.setString(4,vote.getCreationDate());
            return preparedStatement;
        };

        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(preparedStatementCreator, generatedKeyHolder);
        vote.setId(generatedKeyHolder.getKey().intValue());
        return "Success";
    }

    public int GetAllVotesForPosts(int postId, int voteTypeId) {
        final String sqlStatement = "SELECT COUNT(*) FROM votes WHERE PostId = ? AND VoteTypeId = ?";
        return jdbcTemplate.queryForObject(sqlStatement, Integer.class, new Object[] {postId, voteTypeId});
    }

    public int GetAllVotesForUser(int userId, int voteTypeId) {
        final String sqlStatement = "SELECT COUNT(*) FROM votes WHERE UserId = ? AND VoteTypeId = ?";
        return jdbcTemplate.queryForObject(sqlStatement, Integer.class, new Object[] {userId, voteTypeId});
    }

}
