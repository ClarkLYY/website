package com.clarklyy.website.repository.es;

import com.clarklyy.website.domain.entity.Blog;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;



public interface EsBlogRepository extends ElasticsearchRepository<Blog,Integer> {
    Page<Blog> findByTitle(String title, Pageable page);

}
