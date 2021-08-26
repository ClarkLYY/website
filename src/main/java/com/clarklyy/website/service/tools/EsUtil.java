package com.clarklyy.website.service.tools;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.clarklyy.website.domain.entity.Blog;
import com.clarklyy.website.domain.vo.BlogVo;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class EsUtil {
    @Resource
    RestHighLevelClient client;

    //创建索引
    public boolean createIndex(String index) throws IOException {
        //1.创建索引请求
        CreateIndexRequest request = new CreateIndexRequest(index);
        //2.执行客户端请求
        CreateIndexResponse response = client.indices()
                .create(request, RequestOptions.DEFAULT);
        return response.isAcknowledged();
    }

    //删除索引
    public boolean deleteIndex(String index) throws IOException {
        DeleteIndexRequest request = new DeleteIndexRequest(index);
        AcknowledgedResponse delete = client.indices()
                .delete(request, RequestOptions.DEFAULT);
        return delete.isAcknowledged();
    }

    //更新es索引内容
    public void importAll(String index, List<BlogVo> list) throws IOException {
        deleteIndex(index);
        createIndex(index);
        BulkRequest request = new BulkRequest(index);
        for(BlogVo blogVo:list){
            request.add(new IndexRequest(index, "blog",String.valueOf(blogVo.getBlogId())).source(JSONObject.toJSONString(blogVo), XContentType.JSON));
        }

        BulkResponse bulk = client.bulk(request,RequestOptions.DEFAULT);
    }

    //分页查询
    public List<BlogVo> searchListData(String index, SearchSourceBuilder builder) throws IOException {
        SearchRequest request = new SearchRequest(index);

//        //高亮
//        HighlightBuilder highlight = new HighlightBuilder();
//        highlight.field(highlightField);
//        //关闭多个高亮
//        highlight.requireFieldMatch(false);
//        highlight.preTags("<span style='color:red'>");
//        highlight.postTags("</span>");
//        query.highlighter(highlight);
        //不返回源数据。只有条数之类的数据。
        //builder.fetchSource(false);
        request.source(builder);
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        log.info("totalHits:" + response.getHits().getTotalHits());
        List<BlogVo> list = new LinkedList<>();
        for(SearchHit hit: response.getHits()){
            JSONObject blogJson = JSONObject.parseObject(hit.getSourceAsString());
            BlogVo blogVo = JSON.toJavaObject(blogJson, BlogVo.class);

            list.add(blogVo);
        }
        return list;
    }
}
