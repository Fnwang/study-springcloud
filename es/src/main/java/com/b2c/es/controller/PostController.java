package com.b2c.es.controller;

import com.b2c.es.model.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * post控制类
 */
@RestController
public class PostController {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    /**
     * 单字符串模糊查询，默认排序。将从所有字段中查找包含传来的word分词后字符串的数据集
     */
    @RequestMapping("/plist")
    @Cacheable(value = "post", key = "#root.methodName+'==>('+word+')'")
    public Object singleTitle(String word, @PageableDefault Pageable pageable) {

        log.info("从es中查询关键字[{}]的数据==>",word);
        //使用queryStringQuery完成单字符串查询
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(queryStringQuery(word))
                .withPageable(pageable)
                .build();
        return elasticsearchTemplate.queryForList(searchQuery, Post.class);
    }

}
