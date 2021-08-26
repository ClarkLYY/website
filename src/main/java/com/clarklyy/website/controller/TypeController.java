package com.clarklyy.website.controller;

import com.clarklyy.website.common.result.Result;
import com.clarklyy.website.domain.entity.Type;
import com.clarklyy.website.domain.vo.TypesVo;
import com.clarklyy.website.service.TypeService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/type")
public class TypeController {
    @Resource
    TypeService typeService;

    @ApiOperation("获取所有分类")
    @GetMapping("/getAllType")
    public Result getAllType(){
        return Result.success(typeService.getTypeList());
    }

    @ApiOperation("新增分类")
    @PostMapping("/addType")
    public Result addType(@RequestBody Type type){
        typeService.updateOrCreateType(type);
        return Result.success(null);
    }

    @ApiOperation("编辑分类")
    @PostMapping("/editType")
    public Result editType(@RequestBody Type type){
        typeService.updateOrCreateType(type);
        return Result.success(null);
    }

    @ApiOperation("删除分类")
    @GetMapping("/deleteType/{typeId}")
    public Result deleteType(@PathVariable Integer typeId){
        return Result.success(typeService.deleteType(typeId));
    }

    @ApiOperation("分页获取分类")
    @GetMapping("/getTypeByPage")
    public Result getTypeByPage(@RequestParam(defaultValue = "1", value = "pageNo") Integer pageNo,
                                @RequestParam(defaultValue = "5", value = "pageSize") Integer pageSize){

        return Result.success(typeService.getTypeByPage(pageNo, pageSize));
    }

}
