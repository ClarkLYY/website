package com.clarklyy.website.service.impl;

import com.clarklyy.website.domain.entity.Blog;
import com.clarklyy.website.domain.entity.BlogTag;
import com.clarklyy.website.domain.vo.BlogVo;
import com.clarklyy.website.repository.mapper.BlogMapper;
import com.clarklyy.website.repository.mapper.BlogTagMapper;
import com.clarklyy.website.service.BlogService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

@Service
public class BlogServiceImpl implements BlogService {

    @Resource
    BlogMapper blogMapper;

    @Resource
    BlogTagMapper blogTagMapper;

    @Override
    public BlogVo selectBlogById(Integer id) {
        BlogVo blogVo = blogMapper.selectByPrimaryKey(id);
        blogVo.setBlogTagList(blogTagMapper.selectByBlogId(id));
        return blogVo;
    }

    @Override
    public PageInfo<BlogVo> getBlogByPage(Integer pageNo, Integer pageSize) {
        String orderBy = "created desc";
        PageHelper.startPage(pageNo, pageSize, orderBy);
        List<BlogVo> list = blogMapper.selectAllBlog();
        PageInfo<BlogVo> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public void saveOrUpdate(BlogVo blogVo) {
        Integer blogId = blogVo.getBlogId();
        if(blogId!=null){
            blogMapper.updateByPrimary(blogVo);
            blogTagMapper.deleteByBlogId(blogId);
            addBlogTag(blogVo, blogId);

        }else{
            System.out.println(blogVo.toString());
            blogMapper.insert(blogVo);
            addBlogTag(blogVo, blogVo.getBlogId());
        }
    }

    public void addBlogTag(BlogVo blogVo, Integer blogId){
        System.out.println(blogVo);
        if(blogVo.getBlogTagList()==null) return;
        List<Integer> list = blogVo.getBlogTagList();
        for(int tagId:list){
            BlogTag blogTag = new BlogTag();
            blogTag.setBlogId(blogId);
            blogTag.setTagId(tagId);
            blogTagMapper.insert(blogTag);
        }
    }

    @Override
    public Integer deleteBlog(Integer id) {
        if(blogMapper.selectByPrimaryKey(id)!=null){
            blogMapper.deleteByPrimaryKey(id);
            return 0;
        }
        return 1;
    }

    @Override
    public PageInfo<BlogVo> getBlogByTypeId(Integer pageNo, Integer pageSize, Integer typeId) {
        String orderBy = "created desc";
        PageHelper.startPage(pageNo, pageSize, orderBy);
        List<BlogVo> list = blogMapper.selectByTypeId(typeId);
        PageInfo<BlogVo> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public PageInfo<Blog> getBlogByTagId(Integer pageNo, Integer pageSize, Integer tagId) {
        String orderBy = "blog_tag_id desc";
        PageHelper.startPage(pageNo, pageSize, orderBy);
        List<BlogTag> list = blogTagMapper.selectByTagId(tagId);
        List<Blog> result = new LinkedList<>();
        for(BlogTag blogTag:list){
            Blog blog = blogMapper.selectByPrimaryKey(blogTag.getBlogId());
            result.add(blog);
        }
        PageInfo<Blog> pageInfo = new PageInfo<>(result);
        return pageInfo;
    }
}
