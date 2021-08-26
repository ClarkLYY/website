package com.clarklyy.website.service;

import com.clarklyy.website.domain.vo.BlogVo;

import java.io.IOException;
import java.util.List;

public interface EsBlogService {
    /**
     * 同步所有数据到es
     * @param
     * @return Integer
     */
    void importAll(String index) throws IOException;

    /**
     * 删除es数据
     * @param id
     * @return
     */
//    void delete(Integer id);

    /**
     * 模糊搜索es数据
     * @param title,pageNum,pageSize
     * @return Page<Blog>
     */
    List<BlogVo> search(String title, Integer pageNum, Integer pageSize) throws IOException;

    /**
     * 返回所有es数据
     * @param pageNum,pageSize
     * @return Page<Blog>
     */
//    List<BlogVo> searchDefault(Integer pageNum, Integer pageSize) throws IOException;

    /**
     * 同步单个数据
     * @param id
     * @return Blog
     */
//    Blog create(Integer id);
}
