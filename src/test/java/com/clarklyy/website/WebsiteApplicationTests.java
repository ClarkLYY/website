package com.clarklyy.website;

import com.clarklyy.website.domain.entity.Blog;
import com.clarklyy.website.domain.entity.User;
import com.clarklyy.website.repository.mapper.BlogMapper;
import com.clarklyy.website.repository.mapper.UserMapper;
import com.clarklyy.website.service.BlogService;
import com.clarklyy.website.service.tools.JwtFilter;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class WebsiteApplicationTests {

    @Resource
    UserMapper userMapper;

    @Resource
    BlogMapper blogMapper;

    @Resource
    BlogService blogService;

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

    @Test
    void selectByPageTest(){
        List<Blog> list = blogService.selectBlogByPage(2,2);
        for (int i = 0;i<list.size();i++){
            System.out.println(list.get(i).getId());
        }
    }
}
