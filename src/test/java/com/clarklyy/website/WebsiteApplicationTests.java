package com.clarklyy.website;

import com.clarklyy.website.domain.entity.User;
import com.clarklyy.website.repository.mapper.UserMapper;
import com.clarklyy.website.service.tools.JwtFilter;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class WebsiteApplicationTests {

    @Resource
    UserMapper userMapper;

    @Test
    void contextLoads() {
    }

    @Test
    void selectByEmailTest(){
        User user = userMapper.selectByUserEmail("leiyuyang@bytedance.com");
        System.out.println(user.toString());
    }

    @Test
    void jwtFilterTest(){
        JwtFilter jwtFilter = new JwtFilter();
    }
}
