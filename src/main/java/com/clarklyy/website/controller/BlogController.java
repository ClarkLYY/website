package com.clarklyy.website.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import com.clarklyy.website.common.result.Result;
import com.clarklyy.website.common.utils.ShiroUtil;
import com.clarklyy.website.domain.entity.AccountProfile;
import com.clarklyy.website.domain.entity.Blog;
import com.clarklyy.website.domain.entity.User;
import com.clarklyy.website.domain.vo.BlogsVo;
import com.clarklyy.website.service.BlogService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
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
    public Result blogs(@RequestParam(defaultValue = "1", value = "pageNo") Integer pageNo, @RequestParam(defaultValue = "5", value = "pageSize") Integer pageSize){
        BlogsVo blogsVo = new BlogsVo();
        blogsVo.setList(blogService.selectBlogByPage(pageNo-1, pageSize));
        blogsVo.setTotal(blogService.selectBlogByPage(0,100000).size());
        return Result.success(blogsVo);
    }

    @GetMapping("/blog/{id}")
    public Result blog(@PathVariable Integer id){
        Blog blog = blogService.selectBlogById(id);
        if(blog == null){
            return Result.fail("Blog 不存在!");
        }
        return Result.success(blog);
    }

    @RequiresAuthentication
    @PostMapping("/blog/edit")
    public Result edit(@Validated @RequestBody Blog blog){
        Blog temp = null;
        //有id就编辑，没id就新建
        if(blog.getId() != null){
            temp = blogService.selectBlogById(blog.getId());
            if(temp==null){
                return Result.fail("Blog 不存在!");
            }
//            Assert.isTrue(temp.getUserId().intValue() == ShiroUtil.getProfile().getId().intValue(), "没有编辑权限");
        }else{
            System.out.println(ShiroUtil.getProfile().toString());
            AccountProfile accountProfile = ShiroUtil.getProfile();
            temp = new Blog();
            temp.setUserId(ShiroUtil.getProfile().getId());
            temp.setCreated(new Date());
            temp.setStatus(0);
        }
        temp.setCreated(new Date());
        BeanUtil.copyProperties(blog, temp, "id", "userId", "created", "status");
        blogService.saveOrUpdate(temp);
        return Result.success(null);
    }



}
