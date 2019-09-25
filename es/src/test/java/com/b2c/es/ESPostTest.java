package com.b2c.es;

import com.b2c.es.model.GradesModel;
import com.b2c.es.model.Post;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.matchPhraseQuery;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ESPostTest {

    //日志
    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    /**
     *
     */
    @Test
    public void singlePhraseMatch() {
        String content = "李照清";
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(matchPhraseQuery("content", content).slop(2))
                //.withPageable(pageable)
                .build();
        List<Post> list = elasticsearchTemplate.queryForList(searchQuery, Post.class);

        list.forEach(System.out::println);

    }

}
