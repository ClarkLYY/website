package com.clarklyy.website.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class Blog {
    private Integer blogId;

    private Integer userId;

    private String title;

    private String description;

    private String flag;

    private Integer views;

    private Boolean appreciation;

    private String shareStatement;

    private Boolean commented;

    private Boolean published;

    private Boolean recommend;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date created;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date updated;

    private Integer status;

    private Integer typeId;

    private String firstPicture;

    private Integer thumbs;

    private String content;


}