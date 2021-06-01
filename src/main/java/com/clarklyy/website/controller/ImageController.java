package com.clarklyy.website.controller;

import com.clarklyy.website.common.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/img")
public class ImageController {

    @Value("${clark.serverURL}")
    private String serverURL;

    @Value("${server.port}")
    private String port;

    @PostMapping("/upload")
    public Result upImage(@RequestParam("fileName") MultipartFile file){

        if(file.isEmpty()){
            return Result.fail("上传图片为空");
        }
        String fileName = file.getOriginalFilename();

        ApplicationHome home = new ApplicationHome(getClass());
        String path = home.getSource().getParentFile().toString() + "/image";
        System.out.println(path);

        File imgFile = new File(path + "/" + fileName);
        if (!imgFile.getParentFile().exists()){
            imgFile.getParentFile().mkdir();
        }

        try{
            file.transferTo(imgFile);
            return Result.successUploadImg("http://" + serverURL + ":"+ port + "/"+fileName);

        } catch (IOException e){
            log.error(e.getMessage());
            return Result.fail(e.getMessage());
        }
    }

}
