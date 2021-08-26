package com.clarklyy.website.service;

import com.clarklyy.website.domain.entity.Tag;
import com.github.pagehelper.PageInfo;


import java.util.List;

public interface TagService {

    PageInfo<Tag> getTagByTag(Integer pageNo, Integer pageSize);

    List<Tag> getTagList();

    void updateOrCreateTag(Tag tag);

    int deleteTag(int typeId);

}
