package com.StackOverflow.StackOverflow.repository;

import com.StackOverflow.StackOverflow.model.Account;
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
public class AccountRepository {



    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Account AddAccount(Account account) {
        String sqlStatement = "INSERT INTO acounts(Username, Password) values (?,?)";
        PreparedStatementCreator preparedStatementCreator = (connection) -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());
            return preparedStatement;
        };
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(preparedStatementCreator, generatedKeyHolder);

        account.setAccountId(generatedKeyHolder.getKey().intValue());
        return account;
    }

    public Account Login(Account account) {
        String sqlStatement = "SELECT * FROM acounts WHERE Username = ? AND Password = ?";
        RowMapper<Account> mapper =GetAccountRowMapper();

        return GetAccountFromResultSet(jdbcTemplate.query(sqlStatement,mapper, new Object[] {account.getUsername(), account.getPassword()}));
    }

    private Account GetAccountFromResultSet(List<Account> accounts) {

        if(accounts != null && !accounts.isEmpty()) {
            return accounts.get(0);
        } else {
            throw new RuntimeException("Account doesn't exists");
        }
    }
    private RowMapper<Account> GetAccountRowMapper() {

        return((resultSet, rowNum) ->
                new Account(resultSet.getInt("AccountId"),
                        resultSet.getString("Username"),
                        resultSet.getString("Password")));
    }

    public void DeleteAll() {
        String sql = "delete from acounts";
        jdbcTemplate.update(sql);
    }
}
