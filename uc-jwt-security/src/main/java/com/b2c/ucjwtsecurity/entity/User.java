package com.b2c.ucjwtsecurity.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author zhaoxinguo on 2017/9/13.
 */
@Data
@Entity
@Table(name = "tb_user")
public class User {

    @Id
    @GeneratedValue
    private long id;
    private String username;
    private String password;
}
