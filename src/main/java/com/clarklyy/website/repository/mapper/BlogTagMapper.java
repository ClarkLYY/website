package com.clarklyy.website.repository.mapper;

import com.clarklyy.website.domain.entity.BlogTag;

import java.util.List;

public interface BlogTagMapper {
    int deleteByBlogId(Integer blogId);

    int insert(BlogTag record);

    int insertSelective(BlogTag record);

    BlogTag selectByPrimaryKey(Integer blogTagId);

    int updateByPrimaryKeySelective(BlogTag record);

    int updateByPrimaryKey(BlogTag record);

    List<BlogTag> selectByTagId(Integer tagId);

    List<Integer> selectByBlogId(Integer blogId);
}