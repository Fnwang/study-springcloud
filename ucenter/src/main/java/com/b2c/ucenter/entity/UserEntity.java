package com.b2c.ucenter.entity;

import lombok.*;
import javax.persistence.*;
import java.util.List;

/**
 * 用户表
 */
@Data
@Entity
@Table(name = "sys_user")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @NonNull
    private String username;
    @NonNull
    private String password;
    //多对对关系配置
    @ManyToMany(cascade = {CascadeType.REFRESH},fetch = FetchType.EAGER)
    private List<RoleEntity> roles;

    /**
     * 带用户名称和密码的构造函数
     * @param username
     * @param password
     */
    public UserEntity(String username,String password){
        this.username = username;
        this.password = password;
    }
}
