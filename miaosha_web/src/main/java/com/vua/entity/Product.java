package com.vua.entity;

import lombok.Data;

import java.util.Date;

/**
 * @program: miaosha
 * @description:
 * @author: vua
 * @create: 2020-02-29 16:01
 */
/*
create table product(
    id int(10) not null auto_increment,
    image varchar(100) ,
    name varchar(20) not null,
    price float(8) not null,
    number int(8) not null,
    primary key(id)
)
*/
@Data
public class Product {

    private int id;
    private String name;
    private String image;
    private float price;
    private int number;
    private Date start_time;
    private Date end_time;
}
