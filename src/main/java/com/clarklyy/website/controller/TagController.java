package com.clarklyy.website.controller;

import com.clarklyy.website.common.result.Result;
import com.clarklyy.website.domain.entity.Tag;
import com.clarklyy.website.service.TagService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/tag")
public class TagController {

    @Resource
    TagService tagService;

    @ApiOperation("获取所有标签")
    @GetMapping("/getAllTag")
    public Result getAllTag(){
        return Result.success(tagService.getTagList());
    }

    @ApiOperation("分页获取标签")
    @GetMapping("/getTagByPage")
    public Result getTagByPage(@RequestParam(defaultValue = "1", value = "pageNo") Integer pageNo,
                               @RequestParam(defaultValue = "5", value = "pageSize") Integer pageSize){
        return Result.success(tagService.getTagByTag(pageNo,pageSize));
    }

    @ApiOperation("新增标签")
    @PostMapping("/addTag")
    public Result addTag(@RequestBody Tag tag){
        tagService.updateOrCreateTag(tag);
        return Result.success(null);
    }

    @ApiOperation("编辑标签")
    @PostMapping("/editTag")
    public Result editTag(@RequestBody Tag tag){
        tagService.updateOrCreateTag(tag);
        return Result.success(null);
    }

    @ApiOperation("删除标签")
    @GetMapping("/deleteTag/{tagId}")
    public Result deleteTag(@PathVariable(name = "tagId") Integer tagId){
        return Result.success(tagService.deleteTag(tagId));
    }

}
