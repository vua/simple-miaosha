package com.vua.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @program: miaosha
 * @description:
 * @author: vua
 * @create: 2020-03-06 15:35
 */
/*
create table orders(
    id int(10) not null auto_increment,
    uid int(10) not null,
    pid int(10) not null,
    create_time datetime not null,
    primary key(id)
);
*/
@Data
@Entity
@Table(name="orders")
public class Order {
    @Id
    private int id;
    private int uid;
    private int pid;
    private Date create_time;
    private int status;
}
