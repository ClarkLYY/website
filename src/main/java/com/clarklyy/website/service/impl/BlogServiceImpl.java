package com.clarklyy.website.service.impl;

import com.clarklyy.website.domain.entity.Blog;
import com.clarklyy.website.repository.mapper.BlogMapper;
import com.clarklyy.website.service.BlogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class BlogServiceImpl implements BlogService {

    @Resource
    BlogMapper blogMapper;

    @Override
    public Blog selectBlogById(Integer id) {
        return blogMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Blog> selectBlogByPage(Integer pageNo, Integer pageSize) {

        Integer totalPage = pageNo * pageSize;

        System.out.printf("页码为：%d\n",pageNo);
        System.out.printf("分页大小为：%d\n",pageSize);

        return blogMapper.selectByPage(totalPage, pageSize);
    }

    @Override
    public void saveOrUpdate(Blog blog) {
        if(blog.getId()!=null){
            blogMapper.updateByPrimaryKey(blog);
        }else{
            blogMapper.insert(blog);
        }
    }
}
