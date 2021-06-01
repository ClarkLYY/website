package com.clarklyy.website.service.impl;

import com.clarklyy.website.domain.entity.User;
import com.clarklyy.website.service.SendEmail;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class SendEmailImpl implements SendEmail {
    @Resource
    private JavaMailSender mailSender;

    @Value("${clark.serverURL}")
    private String serverURL;

    @Value("${server.port}")
    private String port;

    @Override
    public String sendEmail(User user) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom("2868451714@qq.com");
        helper.setSubject("test email");
        helper.setTo(user.getUserEmail());
        helper.setText("http://"+ serverURL + ":"+ port +"/user/verify?userId="+user.getUserId());
        mailSender.send(message);
        return null;
    }
}
