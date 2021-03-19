package com.StackOverflow.StackOverflow.repository;

import com.StackOverflow.StackOverflow.model.Question;
import com.StackOverflow.StackOverflow.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Repository
public class UserRepository {
    private JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {this.jdbcTemplate = jdbcTemplate;}

    public User AddUser(User user) {
        String sqlStatement = "INSERT INTO users(AboutMe, Age,CreationDate, DisplayName, DownVotes, Location, Reputation, UpVotes,Views, AccountId) values (?,?, ?,?,0,?,0,0,0,?)";
        PreparedStatementCreator preparedStatementCreator = (connection) -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.getAboutMe());
            preparedStatement.setString(2, Integer.toString(user.getAge()));
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            preparedStatement.setString(3, dtf.format(now));
            preparedStatement.setString(4, user.getDisplayName());
            preparedStatement.setString(5,user.getLocation());
            preparedStatement.setString(6,Integer.toString(user.getAccountId()));
            return preparedStatement;
        };

        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(preparedStatementCreator, generatedKeyHolder);
        user.setUserId(generatedKeyHolder.getKey().intValue());
        return user;
    }

    public User Login(int id) {
        String sqlStatement = "SELECT * FROM users u JOIN acounts a on u.AccountId = a.AccountId WHERE u.AccountId = ?";
        RowMapper<User> mapper =GetUserAccountRowMapper();

        return GetUserFromResultSet(jdbcTemplate.query(sqlStatement,mapper, id));
    }

    public User GetUserById(int userId) {
        String sqlStatement = "SELECT * FROM users WHERE Id = ?";
        RowMapper<User> mapper = GetUserRowMapper();
        return GetUserFromResultSet(jdbcTemplate.query(sqlStatement,mapper,userId));
    }

    public User getUserByUsername(String username) {
        String sqlStatement = "SELECT * FROM users WHERE Username = ?";
        RowMapper<User> mapper = GetUserRowMapper();
        return GetUserFromResultSet(jdbcTemplate.query(sqlStatement,mapper,username));
    }

    public String UpdateUserUpVotes(int userId, int upVotesCount) {
        final String sqlStatement = "UPDATE users SET UpVotes = ?  WHERE Id =?";
        PreparedStatementCreator preparedStatementCreator = (connection) -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setString(1,Integer.toString(upVotesCount));
            preparedStatement.setString(2,Integer.toString(userId));
            return preparedStatement;
        };
        jdbcTemplate.update(preparedStatementCreator);
        return "Success";
    }

    public String UpdateUserDownVotes(int userId, int downVotesCount) {
        final String sqlStatement = "UPDATE users SET DownVotes = ?  WHERE Id =?";
        PreparedStatementCreator preparedStatementCreator = (connection) -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setString(1,Integer.toString(downVotesCount));
            preparedStatement.setString(2,Integer.toString(userId));
            return preparedStatement;
        };
        jdbcTemplate.update(preparedStatementCreator);
        return "Success";
    }

    public String UpdateUserReputation(int userId, int reputation) {
        final String sqlStatement = "UPDATE users SET Reputation = ?  WHERE Id =?";
        PreparedStatementCreator preparedStatementCreator = (connection) -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setString(1,Integer.toString(reputation));
            preparedStatement.setString(2,Integer.toString(userId));
            return preparedStatement;
        };
        jdbcTemplate.update(preparedStatementCreator);
        return "Success";
    }
    
    public List<User> GetTopRatedUsers(int maxNumber) {
    	
    	String sqlStatement = "";
    	switch(maxNumber) {
	    	case 0:
	            sqlStatement = "SELECT u.Id, u.AboutMe, u.Age, u.CreationDate," +
	                "u.DisplayName, u.DownVotes, u.Location, " +
	                "u.Reputation, u.UpVotes, u.Views, a.Username FROM Users u " +
	                "LEFT JOIN acounts a on u.AccountId = a.AccountId ORDER BY reputation DESC";
	            break;
	        default:
	            sqlStatement = "SELECT  u.Id, u.AboutMe, u.Age, u.CreationDate," +
	                "u.DisplayName, u.DownVotes, u.Location, " +
	                "u.Reputation, u.UpVotes, u.Views, a.Username FROM Users u " +
	                "LEFT JOIN acounts a on u.AccountId = a.AccountId ORDER BY reputation DESC LIMIT " + maxNumber;
	            break;
    	}
    	
    	RowMapper<User> mapper = GetUserAccountWithoutPasswordRowMapper();
        return GetUsersListFromResultSet(jdbcTemplate.query(sqlStatement,mapper));
    }
    
    
    private List<User> GetUsersListFromResultSet(List<User> users) {

        if(users != null && !users.isEmpty()) {
            return users;
        } else {
            return null;
        }
    }
    
    private User GetUserFromResultSet(List<User> users) {

        if(users != null && !users.isEmpty()) {
            return users.get(0);
        } else {
            throw new RuntimeException("User doesn't exists");
        }
    }

    private RowMapper<User>GetUserAccountRowMapper() {

        return((resultSet, rowNum) ->
                new User(resultSet.getString("Username"),
                        resultSet.getString("Password"),
                        resultSet.getInt("Id"),
                        resultSet.getString("AboutMe"),
                        resultSet.getInt("Age"),
                        resultSet.getDate("CreationDate"),
                        resultSet.getString("DisplayName"),
                        resultSet.getInt("DownVotes"),
                        resultSet.getString("Location"),
                        resultSet.getInt("Reputation"),
                        resultSet.getInt("UpVotes"),
                        resultSet.getInt("Views")));
    }
    
    private RowMapper<User>GetUserAccountWithoutPasswordRowMapper() {

        return((resultSet, rowNum) ->
                new User(resultSet.getString("Username"),
                        resultSet.getInt("Id"),
                        resultSet.getString("AboutMe"),
                        resultSet.getInt("Age"),
                        resultSet.getDate("CreationDate"),
                        resultSet.getString("DisplayName"),
                        resultSet.getInt("DownVotes"),
                        resultSet.getString("Location"),
                        resultSet.getInt("Reputation"),
                        resultSet.getInt("UpVotes"),
                        resultSet.getInt("Views")));
    }

    private RowMapper<User>GetUserRowMapper() {

        return((resultSet, rowNum) ->
                new User(
                        resultSet.getInt("Id"),
                        resultSet.getString("AboutMe"),
                        resultSet.getInt("Age"),
                        resultSet.getDate("CreationDate"),
                        resultSet.getString("DisplayName"),
                        resultSet.getInt("DownVotes"),
                        resultSet.getString("Location"),
                        resultSet.getInt("Reputation"),
                        resultSet.getInt("UpVotes"),
                        resultSet.getInt("Views")));
    }
}


