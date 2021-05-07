package com.clarklyy.website.domain.vo;

import com.clarklyy.website.domain.entity.Blog;
import lombok.Data;

import java.util.List;

@Data
public class BlogsVo {
    List<Blog> list;
    Integer total;
}
