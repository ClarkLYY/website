package com.clarklyy.website.controller;

import com.clarklyy.website.common.result.Result;
import com.clarklyy.website.domain.entity.Blog;
import com.clarklyy.website.domain.vo.BlogsVo;
import com.clarklyy.website.service.BlogService;
import com.clarklyy.website.service.EsBlogService;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
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
    public Result esSearch(@Param("title")String title, @Param("pageNum")Integer pageNum, @Param("pageSize")Integer pageSize) throws UnsupportedEncodingException {
        BlogsVo blogsVo = new BlogsVo();
        if(title.isEmpty()){
            List<Blog> list = esBlogService.searchDefault(pageNum-1, pageSize);
            blogsVo.setList(list);
            blogsVo.setTotal(list.size());
            return Result.success(blogsVo);
        }
        title = URLDecoder.decode(title,"UTF-8");
        List<Blog> list = esBlogService.search(title, pageNum-1, pageSize);
        blogsVo.setList(list);
        blogsVo.setTotal(list.size());
        return Result.success(blogsVo);
    }

    @PostMapping("/refresh")
    public Result esRefresh(){
        return Result.success(esBlogService.importAll());
    }


}
