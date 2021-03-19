package com.StackOverflow.StackOverflow.model;

import java.util.Date;

public class User extends Account {
    private int UserId;
    private String AboutMe;
    private int Age;
    private Date CreationDate;
    private String DisplayName;
    private int DownVotes;
    private String Location;
    private int Reputation;
    private int UpVotes;
    private int Views;

    public User() {}

    public User(String username, String password) {
        super(username,password);
    }

    public User(int userId, String aboutMe, int age, Date creationDate, String displayName, int downVotes, String location, int reputation, int upVotes, int views) {
        UserId = userId;
        AboutMe = aboutMe;
        Age = age;
        CreationDate = creationDate;
        DisplayName = displayName;
        DownVotes = downVotes;
        Location = location;
        Reputation = reputation;
        UpVotes = upVotes;
        Views = views;
    }

    public User(String username, String password,String aboutMe, int age, String displayName, String location) {
        super(username, password);
        AboutMe = aboutMe;
        Age = age;
        DisplayName =displayName;
        Location = location;
    }
    public User(String username, String password, int userId, String aboutMe, int age, Date creationDate, String displayName, int downVotes, String location, int reputation, int upVotes, int views) {
        super(username, password);
        UserId = userId;
        AboutMe = aboutMe;
        Age = age;
        CreationDate = creationDate;
        DisplayName = displayName;
        DownVotes = downVotes;
        Location = location;
        Reputation = reputation;
        UpVotes = upVotes;
        Views = views;
    }

    
    public User(String username, int userId, String aboutMe, int age, Date creationDate, String displayName, int downVotes, String location, int reputation, int upVotes, int views) {
        super(username, "");
        UserId = userId;
        AboutMe = aboutMe;
        Age = age;
        CreationDate = creationDate;
        DisplayName = displayName;
        DownVotes = downVotes;
        Location = location;
        Reputation = reputation;
        UpVotes = upVotes;
        Views = views;
    }
    
    public User(String username, String password, String aboutMe, int age, Date creationDate, String displayName, int downVotes, String location, int reputation, int upVotes, int views) {
        super(username, password);
        AboutMe = aboutMe;
        Age = age;
        CreationDate = creationDate;
        DisplayName = displayName;
        DownVotes = downVotes;
        Location = location;
        Reputation = reputation;
        UpVotes = upVotes;
        Views = views;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public String getAboutMe() {
        return AboutMe;
    }

    public void setAboutMe(String aboutMe) {
        AboutMe = aboutMe;
    }

    public int getAge() {
        return Age;
    }

    public void setAge(int age) {
        Age = age;
    }

    public Date getCreationDate() {
        return CreationDate;
    }

    public void setCreationDate(Date creationDate) {
        CreationDate = creationDate;
    }

    public String getDisplayName() {
        return DisplayName;
    }

    public void setDisplayName(String displayName) {
        DisplayName = displayName;
    }

    public int getDownVotes() {
        return DownVotes;
    }

    public void setDownVotes(int downVotes) {
        DownVotes = downVotes;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public int getReputation() {
        return Reputation;
    }

    public void setReputation(int reputation) {
        Reputation = reputation;
    }

    public int getUpVotes() {
        return UpVotes;
    }

    public void setUpVotes(int upVotes) {
        UpVotes = upVotes;
    }

    public int getViews() {
        return Views;
    }

    public void setViews(int views) {
        Views = views;
    }
}
