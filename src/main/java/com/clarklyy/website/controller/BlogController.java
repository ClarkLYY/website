package com.clarklyy.website.controller;

import cn.hutool.core.bean.BeanUtil;
import com.clarklyy.website.common.result.Result;
import com.clarklyy.website.common.utils.ShiroUtil;
import com.clarklyy.website.domain.entity.AccountProfile;
import com.clarklyy.website.domain.entity.Blog;
import com.clarklyy.website.domain.vo.BlogVo;
import com.clarklyy.website.domain.vo.BlogsVo;
import com.clarklyy.website.service.BlogService;
import com.clarklyy.website.service.EsBlogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Api(tags = "BlogController", description = "博客接口")
@RestController
public class BlogController {

    @Resource
    BlogService blogService;

    @Resource
    EsBlogService esBlogService;

    @ApiOperation("获取博客列表")
    @GetMapping("/getBlogByPage")
    public Result getBlogByPage(@RequestParam(defaultValue = "1", value = "pageNo") Integer pageNo,
                                @RequestParam(defaultValue = "5", value = "pageSize") Integer pageSize){
        return Result.success(blogService.getBlogByPage(pageNo,pageSize));
    }

    @ApiOperation("根据分类获取博客列表")
    @GetMapping("/getBlogByTypeId")
    public Result getBlogByTypeId(@RequestParam(defaultValue = "1", value = "pageNo") Integer pageNo,
                                  @RequestParam(defaultValue = "5", value = "pageSize") Integer pageSize,
                                  Integer typeId){
        return Result.success(blogService.getBlogByTypeId(pageNo,pageSize, typeId));
    }

    @ApiOperation("根据标签获取博客列表")
    @GetMapping("/getBlogByTagId")
    public Result getBlogByTagId(@RequestParam(defaultValue = "1", value = "pageNo") Integer pageNo,
                                  @RequestParam(defaultValue = "5", value = "pageSize") Integer pageSize,
                                  Integer tagId){
        return Result.success(blogService.getBlogByTagId(pageNo,pageSize, tagId));
    }

    @ApiOperation("获取单个博客")
    @GetMapping("/blog/{id}")
    public Result blog(@PathVariable Integer id){
        BlogVo blog = blogService.selectBlogById(id);
        if(blog == null){
            return Result.fail("Blog 不存在!");
        }
        return Result.success(blog);
    }

    @ApiOperation("删除博客")
    @GetMapping("/blog/delete/{id}")
    public Result deleteBlog(@PathVariable Integer id) throws IOException {
        Integer result = blogService.deleteBlog(id);
        esBlogService.importAll("clark");
        if(result!=0){
            return Result.fail("删除失败或博客不存在");
        }
        return Result.success("删除成功");
    }

    @ApiOperation("编辑、新建博客")
    @RequiresAuthentication
    @PostMapping("/blog/edit")
    public Result edit(@Validated @RequestBody BlogVo blogVo) throws IOException {
        BlogVo temp = null;
        //有id就编辑，没id就新建
        if(blogVo.getBlogId() != null){
            temp = blogService.selectBlogById(blogVo.getBlogId());
            if(temp==null){
                return Result.fail("Blog 不存在!");
            }
//            Assert.isTrue(temp.getUserId().intValue() == ShiroUtil.getProfile().getId().intValue(), "没有编辑权限");
        }else{
            System.out.println(ShiroUtil.getProfile().toString());
            AccountProfile accountProfile = ShiroUtil.getProfile();
            temp = new BlogVo();
            temp.setUserId(ShiroUtil.getProfile().getId());
            temp.setCreated(new Date());
            temp.setStatus(0);
        }
        temp.setCreated(new Date());
        BeanUtil.copyProperties(blogVo, temp, "BlogId", "userId", "created", "status");
        blogService.saveOrUpdate(temp);
        esBlogService.importAll("clark");
        return Result.success(null);
    }

}
