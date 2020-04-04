package com.vua.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @program: miaosha
 * @description:
 * @author: vua
 * @create: 2020-02-29 13:28
 */
/*
create table user(
    id int(10) not null auto_increment,
    name varchar(15) not null,
    password varchar(15) not null,
    email varchar(30) not null,
    PRIMARY KEY (id)
);
 */
@Data
@Entity
@Table(name="user")
public class User {
    @Id
    private int id;
    private String name;
    private String password;
    private String email;
}

