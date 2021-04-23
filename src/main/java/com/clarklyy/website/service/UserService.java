package com.clarklyy.website.service;

import com.clarklyy.website.domain.entity.User;
import com.clarklyy.website.domain.vo.UserVo;

public interface UserService {
    /**
     * 注册用户
     * @param user
     * @return
     */
    String registerUser(User user);

    /**
     * 获取用户
     * @param id
     * @return user
     */
    User getUser(Integer id);

    /**
     * 对用户的密码进行加密
     * @param user
     * @return user
     */
    User encryptedPassword(User user);

    /**
     * 对用户进行注册确认
     * @param userId
     * @return String
     */
    String verifyRegister(Integer userId);

    /**
     * 根据邮箱返回用户
     * @param userEmail
     * @return String
     */
    User getUserByUserEmail(String userEmail);
}
