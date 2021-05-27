package com.clarklyy.website.service;

import com.clarklyy.website.domain.entity.Blog;

import java.util.List;

public interface BlogService {

    Blog selectBlogById(Integer id);

    List<Blog> selectBlogByPage(Integer pageNo, Integer pageSize);

    void saveOrUpdate(Blog blog);

    Integer deleteBlog(Integer id);
}
