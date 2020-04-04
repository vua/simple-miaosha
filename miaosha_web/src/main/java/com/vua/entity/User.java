package com.vua.entity;

import lombok.Data;



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
public class User {

    private int id;
    private String name;
    private String password;
    private String email;
}

