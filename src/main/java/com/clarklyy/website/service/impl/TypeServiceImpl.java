package com.clarklyy.website.service.impl;

import com.clarklyy.website.domain.entity.Type;
import com.clarklyy.website.repository.mapper.TypeMapper;
import com.clarklyy.website.service.TypeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TypeServiceImpl implements TypeService {
    @Resource
    TypeMapper typeMapper;

    @Override
    public PageInfo<Type> getTypeByPage(Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        List<Type> list = typeMapper.selectTypeByPage();
        PageInfo<Type> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public List<Type> getTypeList() {
        return typeMapper.selectAllType();
    }

    @Override
    public void updateOrCreateType(Type type) {
        if(type.getTypeId()!=null){
            typeMapper.updateByPrimaryKey(type);
        }else{
            System.out.println(type.toString());
            typeMapper.insert(type);
        }
    }

    @Override
    public int deleteType(int typeId) {
        if(typeMapper.selectByPrimaryKey(typeId)!=null){
            typeMapper.deleteByPrimaryKey(typeId);
            return 0;
        }
        return 1;
    }
}
