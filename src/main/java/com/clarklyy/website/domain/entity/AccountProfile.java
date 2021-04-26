package com.clarklyy.website.domain.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class AccountProfile implements Serializable {

    private Integer id;

    private String username;

    private String avatar;

    private String email;

}
