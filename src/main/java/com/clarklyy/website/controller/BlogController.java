package com.clarklyy.website.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import com.clarklyy.website.common.result.Result;
import com.clarklyy.website.common.utils.ShiroUtil;
import com.clarklyy.website.domain.entity.Blog;
import com.clarklyy.website.domain.entity.User;
import com.clarklyy.website.service.BlogService;
import org.apache.shiro.SecurityUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@RestController
public class BlogController {

    @Resource
    BlogService blogService;

    @GetMapping("/blogs")
    public Result blogs(@RequestParam(defaultValue = "0", value = "pageNo") Integer pageNo, @RequestParam(defaultValue = "5", value = "pageSize") Integer pageSize){
        List<Blog> list = blogService.selectBlogByPage(pageNo, pageSize);
        return Result.success(list);
    }

    @PostMapping("/blog/edit")
    public Result edit(@Validated @RequestBody Blog blog){
        Blog temp = null;
        //有id就编辑，没id就新建
        if(blog.getId() != null){
            temp = blogService.selectBlogById(blog.getId());
//            Assert.isTrue(temp.getUserId().intValue() == ShiroUtil.getProfile().getId().intValue(), "没有编辑权限");
        }else{
            temp = new Blog();
            temp.setUserId(ShiroUtil.getProfile().getId());
            temp.setCreated(new Date());
            temp.setStatus(0);
        }

        BeanUtil.copyProperties(blog, temp, "id", "userId", "created", "status");
        blogService.saveOrUpdate(temp);
        return Result.success(null);
    }



}
