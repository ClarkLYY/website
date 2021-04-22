package com.clarklyy.website.service;

import com.clarklyy.website.domain.entity.User;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;


public interface SendEmail {
    /**
     * 发送邮件
     * @param user
     * @return String
     */
    String sendEmail(User user) throws MessagingException;
}
