package com.StackOverflow.StackOverflow.model;

public class Account {
    private int AccountId;
    private String Username;
    private String Password;

    public Account() {}
    public Account(String username, String password) {
        Username = username;
        Password = password;
    }

    public Account(int accountId, String username, String password) {
        AccountId = accountId;
        Username = username;
        Password = password;
    }

    public int getAccountId() {
        return AccountId;
    }

    public void setAccountId(int accountId) {
        AccountId = accountId;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getUsername() {
        return Username;
    }

    public String getPassword() {
        return Password;
    }
}
