package com.clarklyy.website.service.impl;

import com.clarklyy.website.domain.entity.User;
import com.clarklyy.website.repository.mapper.UserMapper;
import com.clarklyy.website.service.UserService;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    UserMapper userMapper;


    @Override
    public String registerUser(User user) {
        userMapper.insert(user);
        System.out.println(user.getUserId());
        return null;
    }

    @Override
    public User getUser(Integer id) {
        User user = userMapper.selectByPrimaryKey(id);
        return user;
    }

    @Override
    public User encryptedPassword(User user) {
        String salt = UUID.randomUUID().toString();
        Md5Hash md5Hash = new Md5Hash(user.getUserPassword(), salt, 2);
        user.setUserPassword(md5Hash.toString());
        user.setSalt(salt);
        return user;
    }

    @Override
    public String verifyRegister(Integer userId) {
        User user = userMapper.selectByPrimaryKey(userId);
        if(System.currentTimeMillis()-user.getTokenExptime().getTime()>86400000){
            userMapper.deleteByPrimaryKey(userId);
            return "链接已失效";
        }
        user.setActiStatus(1);
        userMapper.updateByPrimaryKey(user);
        return "success!";
    }

    @Override
    public User getUserByUserEmail(String userEmail) {
        User user = userMapper.selectByUserEmail(userEmail);
        return null;
    }


}
