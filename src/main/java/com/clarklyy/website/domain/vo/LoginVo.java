package com.clarklyy.website.domain.vo;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class LoginVo implements Serializable {

    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    private String userEmail;

    @NotBlank(message = "密码不能为空")
    private String userPassword;

}
