package com.b2c.es.esRepository;

import com.b2c.es.model.MemberModel;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends ElasticsearchRepository<MemberModel,String> {

    public List<MemberModel> getByUsernameAndCityAndSex(String username,String city,String sex);
}
