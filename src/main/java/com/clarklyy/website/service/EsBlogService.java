package com.clarklyy.website.service;

import com.clarklyy.website.domain.entity.Blog;
import org.springframework.data.domain.Page;

public interface EsBlogService {
    /**
     * 同步所有数据到es
     * @param
     * @return Integer
     */
    Integer importAll();

    /**
     * 删除es数据
     * @param id
     * @return
     */
    void delete(Integer id);

    /**
     * 模糊搜索es数据
     * @param title,pageNum,pageSize
     * @return Page<Blog>
     */
    Page<Blog> search(String title, Integer pageNum, Integer pageSize);

    /**
     * 同步单个数据
     * @param id
     * @return Blog
     */
    Blog create(Integer id);
}
