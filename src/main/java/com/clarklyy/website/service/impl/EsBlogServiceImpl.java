package com.clarklyy.website.service.impl;

import com.clarklyy.website.domain.vo.BlogVo;
import com.clarklyy.website.repository.mapper.BlogMapper;
import com.clarklyy.website.service.EsBlogService;
import com.clarklyy.website.service.tools.EsUtil;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

@Service
public class EsBlogServiceImpl implements EsBlogService {
    @Resource
    EsUtil esUtil;

    @Resource
    BlogMapper blogMapper;

    @Override
    public void importAll(String index) throws IOException {
        List<BlogVo> list = blogMapper.selectAllBlog();
        esUtil.importAll(index, list);
    }

//    @Override
//    public void delete(Integer id) {
//        Blog blog = blogMapper.selectByPrimaryKey(id);
//        esBlogRepository.delete(blog);
//    }

    @Override
    public List<BlogVo> search(String title, Integer pageNum, Integer pageSize) throws IOException {
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(QueryBuilders.matchQuery("title", title));
        int beginPage = (pageNum-1)*pageSize;
        builder.from(beginPage);
        builder.size(pageSize);
        builder.sort("created", SortOrder.DESC);
        List<BlogVo> page = esUtil.searchListData("clark", builder);
        return page;
    }

//    @Override
//    public List<BlogVo> searchDefault(Integer pageNum, Integer pageSize) throws IOException {
//        SearchSourceBuilder builder = new SearchSourceBuilder();
//        builder.query(QueryBuilders.matchQuery("title", ""));
//        List<BlogVo> page = esUtil.searchListData("clark", builder);
//        return page.stream().sorted((b1,b2)->b2.getCreated().compareTo(b1.getCreated())).collect(Collectors.toList());
//
//    }

//    @Override
//    public Blog create(Integer id) {
//        Blog blog = blogMapper.selectByPrimaryKey(id);
//        esBlogRepository.save(blog);
//        return blog;
//    }
}
