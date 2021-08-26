package com.clarklyy.website.repository.mapper;

import com.clarklyy.website.domain.entity.Blog;
import com.clarklyy.website.domain.vo.BlogVo;

import java.util.List;

public interface BlogMapper {
    int deleteByPrimaryKey(Integer blogId);

    int insert(Blog record);

    BlogVo selectByPrimaryKey(Integer blogId);

    int updateByPrimary(Blog record);

    List<BlogVo> selectAllBlog();

    List<BlogVo> selectByTypeId(Integer typeId);
}