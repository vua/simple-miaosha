package com.vua.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @program: miaosha
 * @description:
 * @author: vua
 * @create: 2020-04-09 17:51
 */
/*
create table payment(
    id int(10) not null auto_increment,
    trade_no varchar(30) not null,
    out_trade_no varchar(30) not null,
    primary key(id)
);
 */
@Data
@Entity
@Table(name = "payment")
public class Payment {
    @Id
    private int id;
    private String trade_no;
    private String out_trade_no;
    //private String total_amount;
}
