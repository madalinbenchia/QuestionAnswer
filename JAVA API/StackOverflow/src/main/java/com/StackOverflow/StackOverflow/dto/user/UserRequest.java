package com.StackOverflow.StackOverflow.dto.user;


public class UserRequest extends AccountRequest {
    private String AboutMe;
    private int Age;
    private String DisplayName;
    private String Location;

    public UserRequest(String username, String password, String aboutMe, int age, String displayName, String location) {
        super(username, password);
        AboutMe = aboutMe;
        Age = age;
        DisplayName = displayName;
        Location = location;
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

    public String getDisplayName() {
        return DisplayName;
    }

    public void setDisplayName(String displayName) {
        DisplayName = displayName;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

}
