package com.clarklyy.website.service.impl;

import com.clarklyy.website.domain.entity.Blog;
import com.clarklyy.website.repository.es.EsBlogRepository;
import com.clarklyy.website.repository.mapper.BlogMapper;
import com.clarklyy.website.service.EsBlogService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EsBlogServiceImpl implements EsBlogService {
    @Resource
    EsBlogRepository esBlogRepository;

    @Resource
    BlogMapper blogMapper;

    @Override
    public Integer importAll() {
        List<Blog> blogList = blogMapper.selectByPage(0,99999);
        esBlogRepository.deleteAll();
        esBlogRepository.saveAll(blogList);
        return blogList.size();
    }

    @Override
    public void delete(Integer id) {
        Blog blog = blogMapper.selectByPrimaryKey(id);
        esBlogRepository.delete(blog);
    }

    @Override
    public List<Blog> search(String title, Integer pageNum, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        Page<Blog> page = esBlogRepository.findByTitle(title, pageable);
        return page.stream().sorted((b1,b2)->b2.getCreated().compareTo(b1.getCreated())).collect(Collectors.toList());
    }

    @Override
    public List<Blog> searchDefault(Integer pageNum, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        Page<Blog> page = esBlogRepository.findAll(pageable);
        return page.stream().sorted((b1,b2)->b2.getCreated().compareTo(b1.getCreated())).collect(Collectors.toList());


    }

    @Override
    public Blog create(Integer id) {
        Blog blog = blogMapper.selectByPrimaryKey(id);
        esBlogRepository.save(blog);
        return blog;
    }
}
