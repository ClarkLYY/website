package com.clarklyy.website.service.impl;
import com.clarklyy.website.domain.entity.Tag;
import com.clarklyy.website.repository.mapper.BlogTagMapper;
import com.clarklyy.website.repository.mapper.TagMapper;
import com.clarklyy.website.service.TagService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {
    @Resource
    TagMapper tagMapper;

    @Override
    public PageInfo<Tag> getTagByTag(Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        List<Tag> list = tagMapper.selectTagByPage();
        PageInfo<Tag> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public List<Tag> getTagList() {
        return tagMapper.selectAllTag();
    }

    @Override
    public void updateOrCreateTag(Tag tag) {
        if(tag.getTagId()!=null){
            tagMapper.updateByPrimaryKey(tag);
        }else{
            System.out.println(tag.toString());
            tagMapper.insert(tag);
        }
    }

    @Override
    public int deleteTag(int tagId) {
        if(tagMapper.selectByPrimaryKey(tagId)!=null){
            tagMapper.deleteByPrimaryKey(tagId);
            return 0;
        }
        return 1;
    }
}
