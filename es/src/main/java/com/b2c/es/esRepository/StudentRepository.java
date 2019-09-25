package com.b2c.es.esRepository;

import com.b2c.es.model.StudentModel;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends ElasticsearchRepository<StudentModel,String> {

}
