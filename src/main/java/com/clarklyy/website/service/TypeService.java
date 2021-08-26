package com.clarklyy.website.service;


import com.clarklyy.website.domain.entity.Type;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface TypeService {

    PageInfo<Type> getTypeByPage(Integer pageNo, Integer pageSize);

    List<Type> getTypeList();

    void updateOrCreateType(Type type);

    int deleteType(int typeId);

}
