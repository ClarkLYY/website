package com.clarklyy.website.domain.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserVo implements Serializable {
    private Integer userId;
    private String userNickname;
    private String userPassword;
    private String userEmail;
    private Integer actiState;
    public static final int ACTIVATION_SUCCESSFUL = 1;
    public static final int ACTIVATION_UNSUCCESSFUL = 0;
    private String actiCode;
    private Date tokenExptime;
    private String salt;
}
