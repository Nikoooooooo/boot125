package com.yc.bean;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("optype")
public enum OpType implements IEnum<String> {
    WITHDRAW("withdraw",1),DEPOSITE("deposite",2),TRANSFER("transfer",3);

    private String key;
    private int value;
    OpType(String key,int value){
        this.key=key;
        this.value=value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return this.key;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
