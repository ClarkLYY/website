package com.clarklyy.website.controller;

import com.clarklyy.website.common.result.Result;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

@RestController
public class GlobalExceptionController {

    @RequestMapping(value = "/unauthorized/{message}")
    public Result unauthorized(@PathVariable String message)throws UnsupportedEncodingException{
        return Result.fail(message);
    }
}
