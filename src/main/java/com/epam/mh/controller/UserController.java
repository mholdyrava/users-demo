package com.epam.mh.controller;

import com.epam.mh.model.AddRoleToUserRequest;
import com.epam.mh.model.AddUserRequest;
import com.epam.mh.model.UserSummary;
import com.epam.mh.entity.User;
import com.epam.mh.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserSummary> getAllUsers() {
        return userService.getAllUsers();
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User addUser(@RequestBody AddUserRequest request) {
        return userService.addUser(request.fullName());
    }

    @GetMapping("/{userId}")
    public User getUser(@PathVariable int userId) {
        return userService.getUser(userId);
    }

    @PostMapping("/{userId}/roles")
    public User addRoleToUser(@PathVariable int userId, @RequestBody AddRoleToUserRequest request) {
        return userService.addRoleToUser(userId, request.roleId());
    }
}