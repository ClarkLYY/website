package com.clarklyy.website.controller;

import com.clarklyy.website.domain.entity.User;
import com.clarklyy.website.domain.vo.UserVo;
import com.clarklyy.website.service.SendEmail;
import com.clarklyy.website.service.UserService;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.mail.MessagingException;


@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    UserService userService;

    @Resource
    SendEmail sendEmail;

    @PostMapping("/register")
    public String register(@RequestBody User user) throws MessagingException {
        userService.encryptedPassword(user);
        userService.registerUser(user);
        sendEmail.sendEmail(user);
        return "success!";
    }

    @GetMapping("/getUser")
    public User getUser(@RequestParam(value = "userId") Integer userId){
        return userService.getUser(userId);
    }

    @GetMapping("/verify")
    public String verifyRegister(@RequestParam(value = "userId")Integer userId){
        userService.verifyRegister(userId);
        return "success!";
    }
}

