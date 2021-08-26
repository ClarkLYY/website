package com.clarklyy.website.service;

import com.clarklyy.website.domain.entity.Blog;
import com.clarklyy.website.domain.vo.BlogVo;
import com.github.pagehelper.PageInfo;

public interface BlogService {

    BlogVo selectBlogById(Integer id);

    PageInfo<BlogVo> getBlogByPage(Integer pageNo, Integer pageSize);

    void saveOrUpdate(BlogVo blogVo);

    Integer deleteBlog(Integer id);

    PageInfo<BlogVo> getBlogByTypeId(Integer pageNo, Integer pageSize, Integer typeId);

    PageInfo<Blog> getBlogByTagId(Integer pageNo, Integer pageSize, Integer tagId);
}
