package com.b2c.es.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * 学生模型
 * indexName = "studentIndex" 索引（相当于数据库名称）
 * type = "student" 类型（相当于表名称）
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "student_index",type = "student",shards = 5,replicas = 1)
public class StudentModel  {
    //学生id
    @Id
    private String id;
    //学生姓名
    private String studentName;
    //家庭地址
    private String address;
    //录取学校
    private String school;
    //高考分数
    private Double number;
}
