package com.clarklyy.website.domain.vo;

import com.clarklyy.website.domain.entity.Type;
import lombok.Data;

import java.util.List;

@Data
public class TypesVo {
    List<Type> list;
    Integer total;
}
