package com.b2c.ucenter.entity;

import lombok.*;

import javax.persistence.*;


/**
 * 角色表
 */
@Data
@Entity
@Table(name="sys_role")
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(length = 50)
    private String name;
}
