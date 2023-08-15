package com.yc.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


import java.io.Serializable;

@Data
@AllArgsConstructor //待所有参数的构造方法
@NoArgsConstructor  //空参数的构造方法
@ToString    //生产toString
@TableName("accounts")
public class Account implements Serializable {
    @TableId(type = IdType.AUTO)
    private int accountid;
    @TableField("balance")
    private double money;
}
