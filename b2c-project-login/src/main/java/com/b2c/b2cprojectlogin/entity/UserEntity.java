package com.b2c.b2cprojectlogin.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 用户
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)

@TableName(value = "user")
public class UserEntity {

    private Integer id;

    private String userName;

    private String passWord;

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", passWord='" + passWord + '\'' +
                '}';
    }
}

