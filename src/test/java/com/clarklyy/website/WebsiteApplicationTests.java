package com.clarklyy.website;

import com.clarklyy.website.repository.es.EsBlogRepository;
import com.clarklyy.website.domain.entity.Blog;
import com.clarklyy.website.domain.entity.User;
import com.clarklyy.website.repository.mapper.BlogMapper;
import com.clarklyy.website.repository.mapper.UserMapper;
import com.clarklyy.website.service.BlogService;
import com.clarklyy.website.service.EsBlogService;
import com.clarklyy.website.service.tools.JwtFilter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SpringBootTest
@Slf4j
class WebsiteApplicationTests {

    @Resource
    UserMapper userMapper;

    @Resource
    BlogMapper blogMapper;

    @Resource
    BlogService blogService;

    @Resource
    EsBlogService esBlogService;

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

    @Test
    void esBlogTest(){
//        esBlogService.create(7);
        Page<Blog> blogPage = esBlogService.search("111",0,5);
        log.debug("aa啊啊啊");
        List<Blog> list = blogPage.getContent();
        for(int i=0;i<list.size();i++){
            System.out.println(list.get(i).toString());
        }
    }

    @Test
    void test(){
        Top top = new Top();
        top.id=1;
        List<Top> list = new ArrayList<>();
        list.add(top);
        LList llist = new LList();
        llist.list = list;
        llist.list = Collections.singletonList(llist.list.get(0));
        System.out.println(llist.list.get(0).id);
    }

    private class LList{
        List<Top> list;

    }
    public class Top{
        int id;
    }

}
