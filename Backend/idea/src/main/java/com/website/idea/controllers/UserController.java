package com.website.idea.controllers;

import com.website.idea.models.Users.User;
import com.website.idea.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public User register(@RequestParam String login, @RequestParam String password, @RequestParam String email, @RequestParam String username) {
        return userService.register(login, password, email, username);
    }

    @PostMapping("/login")
    public User login(@RequestParam String loginOrEmail, @RequestParam String password) {
        return userService.login(loginOrEmail, password);
    }
    @PutMapping("/{userId}")
    public User updateUser(@PathVariable Long userId, @RequestParam(required = false) String username, @RequestParam(required = false) String email, @RequestParam(required = false) String password, @RequestParam(required = false) String login) {
        return userService.updateUser(userId, username, email, password, login);
    }
}
