package com.b2c.es.esRepository;

import com.b2c.es.model.GradesModel;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GradesRepository extends ElasticsearchRepository<GradesModel,String> {

}
