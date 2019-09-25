package com.b2c.es.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;

/**
 * 会员模块
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
//mysql：如果数据库表名称和类名称一样（不分大小写），可以不定义
@TableName("member")
//ElasticSearch：主要用于模糊查询
@Document(indexName = "member_index",type = "member",shards = 5,replicas = 1)
public class MemberModel implements Serializable {

    @Id
    @TableId(value = "id",type = IdType.UUID)
    String id;
    String username;
    String sex;
    String city;
    String sign;
    float  experience;
    String classify;
    float  wealth;
    float  score;
}
