package com.clarklyy.website.service.impl;

import com.clarklyy.website.domain.entity.Blog;
import com.clarklyy.website.repository.mapper.BlogMapper;
import com.clarklyy.website.service.BlogService;

import javax.annotation.Resource;
import java.util.List;

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
        System.out.printf("分页大小为：%d大\n",pageSize);

        return blogMapper.selectByPage(totalPage, pageSize);
    }
}
