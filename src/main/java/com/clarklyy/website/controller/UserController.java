package com.clarklyy.website.controller;

import cn.hutool.crypto.SecureUtil;
import com.clarklyy.website.common.result.Result;
import com.clarklyy.website.common.utils.JwtUtils;
import com.clarklyy.website.domain.entity.User;
import com.clarklyy.website.domain.vo.LoginVo;
import com.clarklyy.website.service.SendEmail;
import com.clarklyy.website.service.UserService;
import com.clarklyy.website.service.tools.JwtToken;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    UserService userService;

    @Resource
    JwtUtils jwtUtils;

    @Resource
    SendEmail sendEmail;

    @PostMapping("/register")
    public Result register(@Validated @RequestBody User user) throws MessagingException {
        if (userService.getUserByUserEmail(user.getUserEmail())!=null){
            return Result.fail("该邮箱已被注册！");
        }
        userService.encryptedPassword(user);
        userService.registerUser(user);
        sendEmail.sendEmail(user);
        return Result.success("请到邮箱查收确认注册邮件");
    }

    @RequiresAuthentication
    @GetMapping("/getUser")
    public Object getUser(@RequestParam(value = "userId") Integer userId){
        if (userService.getUser(userId)==null){
            return "该用户id不存在！";
        }
        return userService.getUser(userId);
    }

    @GetMapping("/verify")
    public Result verifyRegister(@RequestParam(value = "userId")Integer userId){
        String status = userService.verifyRegister(userId);
        return Result.success(status);
    }

    @RequiresAuthentication
    @PostMapping("/login")
    public Result login(@Validated @RequestBody LoginVo loginVo, HttpServletResponse response){
//        UsernamePasswordToken token = new UsernamePasswordToken(userEmail, userPassword);
//        Subject subject = SecurityUtils.getSubject();
//        try {
//            subject.login(token);
//        } catch (IncorrectCredentialsException ice) {
//            return "用户名或密码错误！";
//        } catch (UnknownAccountException uae) {
//            return "用户名或密码错误!";
//        }catch (AccountException ae) {
//            return "该邮箱未确认注册";
//        }
//        User user = userService.getUserByUserEmail(userEmail);
//        subject.getSession().setAttribute("user", user);
//        return "SUCCESS";

        User user = userService.getUserByUserEmail(loginVo.getUserEmail());
        if(user == null){
            return Result.fail("用户不存在！");
        }
        if(user.getActiStatus()!=1){
            return Result.fail("邮箱未确认注册！");
        }
        if(!user.getUserPassword().equals(SecureUtil.md5(loginVo.getUserPassword()))){
            return Result.fail("密码错误!");
        }
        Integer userId = user.getUserId();
        String jwt = jwtUtils.generateToken(userId);
        response.setHeader("Authorization", jwt);
        response.setHeader("Access-control-Expose-Headers", "Authorization");

        return Result.success("登录成功");
    }

    @RequiresAuthentication
    @GetMapping("/logout")
    public Result logout(){
        SecurityUtils.getSubject().logout();
        return Result.success("成功退出");
    }
}

