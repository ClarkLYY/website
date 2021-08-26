package com.clarklyy.website.controller;

import com.clarklyy.website.common.result.Result;
import com.clarklyy.website.domain.entity.Blog;
import com.clarklyy.website.domain.vo.BlogVo;
import com.clarklyy.website.domain.vo.BlogsVo;
import com.clarklyy.website.service.BlogService;
import com.clarklyy.website.service.EsBlogService;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.List;

@RequestMapping("/es")
@RestController
public class EsBlogController {

    @Resource
    EsBlogService esBlogService;

    @Resource
    BlogService blogService;

    @GetMapping("/search")
    public Result esSearch(@Param("title")String title, @Param("pageNum")Integer pageNum, @Param("pageSize")Integer pageSize) throws IOException {
        BlogsVo blogsVo = new BlogsVo();
        System.out.println(title);
//        if(title.isEmpty()){
//            return Result.success(blogService.getBlogByPage(pageNum,pageSize));
//        }
        title = URLDecoder.decode(title,"UTF-8");
        List<BlogVo> list = esBlogService.search(title, pageNum, pageSize);
        List<BlogVo> totalList = esBlogService.search(title,1,9999);
        blogsVo.setList(list);
        blogsVo.setTotal(totalList.size());
        return Result.success(blogsVo);
    }

    @PostMapping("/refresh")
    public Result esRefresh() throws IOException {
        esBlogService.importAll("clark");
        return Result.success(1);
    }


}
