package com.clarklyy.website;

//import com.clarklyy.website.repository.es.EsBlogRepository;
import com.clarklyy.website.domain.entity.Blog;
import com.clarklyy.website.domain.entity.User;
import com.clarklyy.website.domain.vo.BlogVo;
import com.clarklyy.website.repository.mapper.BlogMapper;
import com.clarklyy.website.repository.mapper.BlogTagMapper;
import com.clarklyy.website.repository.mapper.UserMapper;
import com.clarklyy.website.service.BlogService;
import com.clarklyy.website.service.EsBlogService;
import com.clarklyy.website.service.tools.EsUtil;
import com.clarklyy.website.service.tools.JwtFilter;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@SpringBootTest
@Slf4j
class WebsiteApplicationTests {

    @Resource
    UserMapper userMapper;

    @Resource
    BlogTagMapper blogTagMapper;

    @Resource
    BlogMapper blogMapper;

    @Resource
    BlogService blogService;

//    @Resource
//    EsBlogService esBlogService;

    @Resource
    RestHighLevelClient client;

    @Resource
    EsUtil esUtil;

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
//        List<Blog> list = blogService.selectBlogByPage(2,2);
//        for (int i = 0;i<list.size();i++){
//            System.out.println(list.get(i).getBlogId());
//        }
    }

    @Test
    void esBlogTest() throws IOException {
//        esBlogService.create(7);
//        List<Blog> list = esBlogService.search("111",0,5);
//        System.out.println(client);
//        esUtil.createIndex("clark");
//        esUtil.importAll("clark", blogService.selectBlogByPage(1,9999));
//        blogService.selectBlogByPage(1, 999);
//         创建
//        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
//        boolQueryBuilder.filter(QueryBuilders.wildcardQuery("title", ""));
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(QueryBuilders.matchQuery("title", ""));
        List<BlogVo> list = esUtil.searchListData("clark", builder);
        for(Blog blog:list){
            System.out.println(blog.toString());
        }

    }

    @Test
    void blogTest(){
        Blog blog = new Blog();
        blog.setTitle("1123asdfasd");
        blog.setUserId(9);
        blog.setDescription("123134123");
        blog.setCreated(new Date());
        blogMapper.insert(blog);
        int i=blog.getBlogId();
        System.out.println(i);
    }

}
