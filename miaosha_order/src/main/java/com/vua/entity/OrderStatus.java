package com.vua.entity;

public enum OrderStatus {
    /*
    * NONPAYMENT:未支付
    * PAYMENT:支付完成
    * SUCCESS:库存
    * FAIL:
    * */
    NONPAYMENT(-1),PAYMENT(0),SUCCESS(1),FAIL(2);
    int val;
    OrderStatus(int val){
        this.val=val;
    }
    public int getVal(){
        return val;
    }
}
