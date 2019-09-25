package com.b2c.b2cprojectlogin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 会员实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName(value = "member")
public class MemberEntity {

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    //名称
    private String username;
    //性别
    private String sex;
    // 城市
    private String city;
    //签名
    private String sign;
    //积分
    private float experience;
    //职业
    private String classify;
    //财富
    private float wealth;
    //评分
    private float score;


    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", sex='" + sex + '\'' +
                ", city='" + city + '\'' +
                ", sign='" + sign + '\'' +
                ", experience=" + experience +
                ", classify='" + classify + '\'' +
                ", wealth=" + wealth +
                ", score=" + score +
                '}';
    }
}
