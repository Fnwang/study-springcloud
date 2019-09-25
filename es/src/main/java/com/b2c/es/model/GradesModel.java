package com.b2c.es.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * 年级
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "grades_index",type = "grades",shards = 5,replicas = 1)
public class GradesModel {

    //id
    @Id
    private String id;

    private String gradesName;

    private String des;

}

