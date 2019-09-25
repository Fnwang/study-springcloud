package com.b2c.es.service.esService;

import com.b2c.es.esRepository.MemberRepository;
import com.b2c.es.model.MemberModel;
import org.elasticsearch.index.query.QueryBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * ES服务
 */
@Service
public class MemberServiceES {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    //100条一批次,用于分小批次存储数据
    //如果数据量超级大，需要拆分可以这么做
    private static final int size  = 100;

    //member ES高性能检索接口
    @Autowired
    MemberRepository memberRepository;

    /**
     * 更新或保存es数据（单条）
     * @param memberModel
     * @return
     */
    public MemberModel addOrUpdateES(MemberModel memberModel){
        //只存储必要到字段到es中，主要用于检索，提高搜索性能
        //保存数据到 es
        MemberModel esm = this.getESMemberModel(memberModel);
        memberRepository.save(esm);

        log.info("es中数据："+esm.toString());

        return esm;
    }

    /**
     * 更新或保存es数据（批量）
     * @param memberModelList
     * @return
     */
    public boolean addOrUpdateES(List<MemberModel> memberModelList){

        List<MemberModel> subList = new ArrayList<MemberModel>();
        //循环批量数据
        for (MemberModel esmIndex : memberModelList) {
            log.info(esmIndex.toString());
            //撞到list中,只存储必要到字段到es中，主要用于检索，提高搜索性能
            subList.add(this.getESMemberModel(esmIndex));
        }
        memberRepository.saveAll(subList);
        return true;
    }


    /**
     * 更新或保存es数据（批量）
     * @param memberModelList
     * @return
     */
    public boolean addOrUpdateESPC(List<MemberModel> memberModelList){
        //开始位置
        int count = 0 ;
        //存储小批次数据
        List<MemberModel> subList = new ArrayList<MemberModel>();
        //循环批量数据
        for (MemberModel esmIndex : memberModelList) {
            //撞到list中
            //只存储必要到字段到es中，主要用于检索，提高搜索性能
            subList.add(this.getESMemberModel(esmIndex));
            count++;
            if (count % size ==0) {
                //分批次存储保存数据到 es
                memberRepository.saveAll(subList);
                log.info("本次保持【{}】条",size);
                //恢复
                count =0;
                subList.clear();
            }
        }
        //如果大批比小批次小，直接直接一次性执行
        if (count<size){
            memberRepository.saveAll(subList);
        }

        return true;
    }

    /**
     * 模糊搜索 -不分页
     * @param key
     * @return
     */
    public Iterable<MemberModel> searchES(String key){

        log.info("ES搜索关键字："+key);
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.queryStringQuery(key))
                .build();
        Iterable<MemberModel> list = memberRepository.search(searchQuery);

        log.info("ES搜索结果：");
        list.forEach(System.out::println);

        return list;
    }

    /**
     * 通过id删除数据
     * @param id
     * @return
     */
    public boolean delES(String id) {
        memberRepository.deleteById(id);
        log.info("es数据已被删除。");
        return true;
    }

    /**
     * 取得mapper实现
     * @return
     */
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }


    /**
     * ES需要的字段（只需要核心的，用于检索的字段）
     * @param memberModel
     * @return
     */
    private MemberModel getESMemberModel(MemberModel memberModel ) {
        MemberModel esmb = new MemberModel();
        //两边id一定要保证一致性
        esmb.setId(memberModel.getId());
        esmb.setUsername(memberModel.getUsername());
        esmb.setSex(memberModel.getSex());
        esmb.setCity(memberModel.getCity());
        esmb.setSign(memberModel.getSign());
        return esmb;
    }

}
