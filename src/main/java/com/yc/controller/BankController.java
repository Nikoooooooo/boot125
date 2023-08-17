package com.yc.controller;

import com.yc.bean.Account;
import com.yc.bean.OpRecord;
import com.yc.biz.AccountBiz;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class BankController {

    //使用http://localhost:8888/swagger-ui/#/ 访问api公开数据
    @Autowired
    private AccountBiz accountBiz;

    @PostMapping("findOpRecord")
    @ApiOperation(value = "查询指定日期的日志")
    public Map findOpRecord(String date){
        Map result=new HashMap();
        try {
            List<OpRecord> list=this.accountBiz.findToday(date);
            result.put("code",1);
            result.put("data",list);
        }catch (RuntimeException e){
            result.put("code",0);
            result.put("errMsg",e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    @ApiOperation("开户")
    @PostMapping("/openAccount")
    public Account openAccount(double money){
        return accountBiz.openAccount(money);
    }

    @ApiOperation("存款")
    @PostMapping("/deposite")
    public Account deposite(int accountid,double money ){
        return accountBiz.deposite(accountid,money);
    }

    @ApiOperation("取款")
    @PostMapping("/withdraw")
    public Account withdraw(int accountid,double money){
        return accountBiz.withdraw(accountid,money);
    }

    @ApiOperation("转账")
    @PostMapping("/transfer")
    public Account transfer(int accountid,double money,int toAccountId){
        return accountBiz.transfer(accountid,money,toAccountId);
    }

    @ApiOperation("查询账户")
    @PostMapping("/findAccount")
    public Account findAccount(int accountId){
        return accountBiz.findAccount(accountId);
    }

}
