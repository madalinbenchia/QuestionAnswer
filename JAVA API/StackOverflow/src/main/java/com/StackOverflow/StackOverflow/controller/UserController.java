package com.StackOverflow.StackOverflow.controller;

import com.StackOverflow.StackOverflow.dto.user.AccountRequest;
import com.StackOverflow.StackOverflow.dto.user.UserRequest;
import com.StackOverflow.StackOverflow.exception.user.UserNotFoundException;
import com.StackOverflow.StackOverflow.mapper.UserMapper;
import com.StackOverflow.StackOverflow.model.User;
import com.StackOverflow.StackOverflow.service.UserService;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/user")
@Api(value = "/user",
        tags = "User Controller")
public class UserController {
        private final UserMapper userMapper;
        private final UserService userService;

        public UserController(UserMapper userMapper, UserService userService) {
            this.userMapper = userMapper;
            this.userService = userService;
        }

        @PostMapping("/register")
        @ApiOperation(value = "Add a new User",
                notes = "Add a User based on the information received from request")
        @ApiResponses(value = {
                @ApiResponse(code = 201, message = "The User was successfully added based on the received request"),
                @ApiResponse(code = 400, message = "Validation error on the received request"),
                @ApiResponse(code = 417, message = "Information provided in the request doesn't not meet the condition.")
        })
        public ResponseEntity<User> AddAccount(
                @RequestBody
                @ApiParam(name = "userObject", value = "user details",required = true)
                        UserRequest userRequest) {
            User addedUser =userService.AddUser(
                    userMapper.userRequestToUser(userRequest));

            return ResponseEntity
                    .created(URI.create("/user/register/" + addedUser.getAccountId()))
                    .body(addedUser);
        }

        @GetMapping("/get/{id}")
        @ApiOperation(value = "Get a specific User",
                notes = "Get a specific User based on the information received from request")
        @ApiResponses(value = {
                @ApiResponse(code = 200, message = "The User was successfully get based on the received request"),
                @ApiResponse(code = 400, message = "Validation error on the received request"),
                @ApiResponse(code = 417, message = "Information provided in the request doesn't not meet the condition.")
        })
        public ResponseEntity<Object> GetUserById(
                @PathVariable
                @ApiParam(name = "id", value = "user id", required = true)
                        int id) {
           try {
               User user = userService.GetUserById(id);
               return ResponseEntity
                       .ok()
                       .body(user);
           }catch(Exception ex) {
               return ResponseEntity
                       .status(HttpStatus.EXPECTATION_FAILED)
                       .body(ex.getMessage());
           }
        }



        @PostMapping("/login")
        @ApiOperation(value = "Login a User",
                notes = "Login a User based on the information received from request")
        @ApiResponses(value = {
                @ApiResponse(code = 201, message = "The User was successfully logged based on the received request"),
                @ApiResponse(code = 400, message = "Validation error on the received request"),
                @ApiResponse(code = 417, message = "Information provided in the request doesn't not meet the condition.")
        })
        public ResponseEntity<Object> Login (
                @RequestBody
                @ApiParam(name = "userObject", value = "user details", required = true)
                        AccountRequest accountRequest) {

                try {
                    User checkedUser = userService.GetUser(userMapper.accountRequestToUser(accountRequest));
                    return ResponseEntity
                            .created(URI.create("/user/login/" + checkedUser.getUserId()))
                            .body(checkedUser);

                }
                catch(Exception ex) {
                    return ResponseEntity
                            .status(HttpStatus.EXPECTATION_FAILED)
                            .body(ex.getMessage());
                }

            }


}
