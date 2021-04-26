package com.clarklyy.website.controller;

import com.clarklyy.website.common.result.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BlogController {



    @GetMapping("/blogs")
    public Result blogs(@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize){

        return Result.success(null);

    }

}
