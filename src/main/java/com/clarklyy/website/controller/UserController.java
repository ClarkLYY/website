package com.clarklyy.website.controller;

import com.clarklyy.website.domain.entity.User;
import com.clarklyy.website.service.SendEmail;
import com.clarklyy.website.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import java.util.HashMap;
import java.util.Map;


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
        String status = userService.verifyRegister(userId);
        return status;
    }

    @GetMapping("/login")
    public String login(@RequestParam(value = "userEmail")String userEmail, @RequestParam("userPassword")String userPassword){
        UsernamePasswordToken token = new UsernamePasswordToken(userEmail, userPassword);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
        } catch (IncorrectCredentialsException ice) {
            return "password error!";
        } catch (UnknownAccountException uae) {
            return "username error!";
        }
        User user = userService.getUserByUserEmail(userEmail);
//        subject.getSession().setAttribute("user", user);
        return "SUCCESS";
    }

    @GetMapping("/unauth")
    public Object unauth() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", "1000000");
        map.put("msg", "未登录");
        return map;
    }
}

