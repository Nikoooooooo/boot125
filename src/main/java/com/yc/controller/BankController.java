package com.yc.controller;

import com.yc.bean.Account;
import com.yc.biz.AccountBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BankController {
    @Autowired
    private AccountBiz accountBiz;

    @PostMapping("/openAccount")
    public Account openAccount(double money){
        return accountBiz.openAccount(money);
    }

    @PostMapping("/deposite")
    public Account deposite(int accountid,double money ){
        return accountBiz.deposite(accountid,money);
    }

    @PostMapping("/withdraw")
    public Account withdraw(int accountid,double money){
        return accountBiz.withdraw(accountid,money);
    }

    @PostMapping("/transfer")
    public Account transfer(int accountid,double money,int toAccountId){
        return accountBiz.transfer(accountid,money,toAccountId);
    }

    @PostMapping("/findAccount")
    public Account findAccount(int accountId){
        return accountBiz.findAccount(accountId);
    }

}
