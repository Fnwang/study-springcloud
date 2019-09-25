package com.b2c.es.model;

import io.searchbox.annotations.JestId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
//@Document(indexName = "employee_index",type = "employee",shards = 5,replicas = 1)
public class Employee {

    @JestId
    Integer id;
    String name;
    Float age;

}
