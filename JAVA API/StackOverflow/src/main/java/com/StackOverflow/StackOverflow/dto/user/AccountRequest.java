package com.StackOverflow.StackOverflow.dto.user;


import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class AccountRequest {
    @NotNull
    @NotBlank
    @NotEmpty
    @ApiModelProperty(value = "username", required = true, notes = "Username", example = "user1", position = 1)
    private String Username;

    @NotNull
    @NotBlank
    @NotEmpty
    @ApiModelProperty(value = "password", required = true, notes = "Username", example = "pass1", position = 2)
    private String Password;

    public AccountRequest() {}
    public AccountRequest(String username, String password) {
        Username = username;
        Password = password;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
