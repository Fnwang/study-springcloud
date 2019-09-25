package com.b2c.es;


import com.b2c.es.model.GradesModel;

import com.b2c.es.esRepository.GradesRepository;

import com.b2c.es.model.MemberModel;
import org.elasticsearch.index.query.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.List;


/**
 * 测试ES-年级模块
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ESGradesTest {

    //日志
    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    //年级
    @Autowired
    GradesRepository gradesRepository;

    //es通用API
    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;


    /**
     * 删除索引,包括类型、数据、请慎重操作
     */
    @Test
    public void removeIndex(){


        //删除索引下所有内容，包括数据
        boolean rs = elasticsearchTemplate.deleteIndex(MemberModel.class);

        log.info("删除索引结果：=》"+rs);
    }


    //一、主要查询方法介绍

    /**
     * 全部搜索出来
     */
    @Test
    public void selectAll(){
        Iterable<GradesModel> list = gradesRepository.findAll();
        list.forEach(System.out::println);
    }


    /**
     * 模糊匹配：全文检索,匹配度最高的自动排列在前，可匹配分词器
     */
    @Test
    public void testStringQuery(){
        String keyWord = "毕业生";
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.queryStringQuery(keyWord))
                .build();
        Iterable<GradesModel> list = gradesRepository.search(searchQuery);
        list.forEach(System.out::println);

        // 使用elasticsearchTemplate方式调用，如下
        // List<GradesModel> list = elasticsearchTemplate.queryForList(searchQuery,GradesModel.class);
    }

    /**
     * 模糊匹配：对某个字段就行搜索
     * 不加slop时候，类似like查询
     * 加上slop时候，启用分词，临近相似都可查询出来
     */
    @Test
    public void testMachPhraseQuery(){
        String keyWord = "毕业生";
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchPhraseQuery("des",keyWord).slop(2))
                .build();
        Iterable<GradesModel> list = gradesRepository.search(searchQuery);
        list.forEach(System.out::println);
    }


    /**
     * 精准查询，匹配单个数字
     */
    @Test
    public void testTermQueryNumber01(){
        //单个值
        String id = "1";

        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.termQuery("id", id))
                .build();
        Iterable<GradesModel> list = gradesRepository.search(searchQuery);
        list.forEach(System.out::println);

    }

    /**
     * 精准查询，匹配多个数字
     */
    @Test
    public void testTermQueryNumber02(){
        //多个值
        String ids[] = {"1","3"};

        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.termsQuery("id", ids))
                .build();
        Iterable<GradesModel> list = gradesRepository.search(searchQuery);
        list.forEach(System.out::println);

    }

    /**
     * 精准查询,匹配字符串，单个
     * 需要注意格式：字段名称.keyword
     */
    @Test
    public void testTermQueryKeyword01(){
        String keyWord = "大四";
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.termQuery("gradesName.keyword", keyWord))
                .build();
        Iterable<GradesModel> list = gradesRepository.search(searchQuery);
        list.forEach(System.out::println);

    }

    /**
     * 精准查询,匹配字符串，多个
     * 需要注意格式：字段名称.keyword
     * 匹配多个关键字，其中一个满足即可。
     * 类似in（.....）查询
     */
    @Test
    public void testTermQueryKeyword02(){
        String keyWords[] = {"大四","新生","大学生","大一"};
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.termsQuery("gradesName.keyword", keyWords))
                .build();
        Iterable<GradesModel> list = gradesRepository.search(searchQuery);
        list.forEach(System.out::println);
    }


    /**
     * 模糊查询：
     */
    @Test
    public void testMoreLikeThisQuery(){
        //字段
        String fieldNames [] = {"gradesName","des"};
        //字段值
        String fieldValues[] = {"大"};
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.moreLikeThisQuery(fieldValues))
                .build();
        Iterable<GradesModel> list = gradesRepository.search(searchQuery);
        list.forEach(System.out::println);
    }



    /**
     * matchQuery
     */
    @Test
    public void testMathQuery(){
        String keyWord = "新生";
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchQuery("des", keyWord)
                    //.operator(Operator.AND)
                    .minimumShouldMatch("20%")
                )
                .build();
        Iterable<GradesModel> list = gradesRepository.search(searchQuery);
        list.forEach(System.out::println);

    }

    /**
     * 多个字段匹配一个关键字，只要其中一个符合即可查询
     * 第一个参数必传，第二个参数是可变参数
     * 类似：gradesName匹配xxxx or des匹配xxxx
     * 如果可变参数不传值，默认or全部参数，类似于queryStringQuery查询
     */
    @Test
    public void testMultilMathQuery() {
       String keyWord = "新生";
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.multiMatchQuery(keyWord,"gradesName","des")
                        .type(MultiMatchQueryBuilder.Type.MOST_FIELDS))
                .build();
        Iterable<GradesModel> list = gradesRepository.search(searchQuery);
        list.forEach(System.out::println);

    }

    /**
     * 多个字段匹配一个关键字，只要其中一个符合即可查询
     * 第一个参数必传，第二个参数是可变参数
     * 默认情况下类似：gradesName匹配xxxx or des匹配xxxx
     * 带上operator后，必须包含关键字
     */
    @Test
    public void testMultilMathQueryOprator(){
        String keyWord = "新生";
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.multiMatchQuery(keyWord,"gradesName","des")
                        .operator(Operator.AND)
                        .minimumShouldMatch("80%")
                )

                .build();
        Iterable<GradesModel> list = gradesRepository.search(searchQuery);
        list.forEach(System.out::println);

    }



    //二、分页





    //三、混合查询


    /**
     * 模糊查询，支持通配符
     */
    @Test
    public void selectwildcardQuery(){
        //查询条件
        DisMaxQueryBuilder disMaxQueryBuilder = QueryBuilders.disMaxQuery();
        //设置权重
        QueryBuilder queryBuilder01  = QueryBuilders.matchQuery("gradesName", "三级").boost(2f);

        disMaxQueryBuilder.add(queryBuilder01);
        //分页
        Pageable pageable = PageRequest.of(0, 5);

        //调用方法
        Page<GradesModel> page = gradesRepository.search(disMaxQueryBuilder,pageable);
        log.info("共有：[{}]条信息",page.getTotalElements());
        log.info("共有：[{}]页",page.getTotalPages());
        //当前页打印输出
        List<GradesModel> list = page.getContent();
        list.forEach(System.out::println);
    }
}
