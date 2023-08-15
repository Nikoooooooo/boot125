package com.yc.bean;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@TableName("oprecord")
public class OpRecord {
    @TableId(type = IdType.AUTO)
    private int id;
    private int accountid;
    private double opmoney;
    @TableField(fill = FieldFill.INSERT)
    private String optime;    //数据库中是datatime,在java中转为String
    @TableField("optype")
    private OpType opType;   //OpType类型
    private Integer transferid;
}
