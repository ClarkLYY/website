package com.clarklyy.website.domain.vo;

import com.clarklyy.website.domain.entity.Blog;
import lombok.Data;

import java.util.List;

@Data
public class BlogVo extends Blog {
    private String typeName;
    private String nickname;
    private String avatar;
    private List<Integer> blogTagList;
}
