package com.clarklyy.website.repository.mapper;

import com.clarklyy.website.domain.entity.Blog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BlogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Blog record);

    int insertSelective(Blog record);

    Blog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Blog record);

    int updateByPrimaryKeyWithBLOBs(Blog record);

    int updateByPrimaryKey(Blog record);

    List<Blog> selectByPage(@Param("pageNo") Integer pageNo, @Param("pageSize")Integer pageSize);
}