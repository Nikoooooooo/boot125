package com.yc.biz;

import com.yc.bean.Account;
import com.yc.bean.OpRecord;

import java.util.List;

public interface AccountBiz {
    /*银行开户*/
    public Account openAccount(double money);


    public Account deposite(int accountId,double money);
    /**
     * 存款:  给account存入money  并且返回最后的余额信息
     */
    Account deposite(int accountId,double money,Integer transferid);

    public Account withdraw(int accountId,double money);

    /**
     * 取款 给accountId 取出money 并返回最后的余额信息
     */
    public Account withdraw(int accountId,double money,Integer transferid);

    //从 accountId中转出 money到toaccountId账户
    public Account transfer(int accountId,double money,int toaccountId);

    //查询是否存在accountId账户
    public Account findAccount(int accountId);

    //查询全部金额
    public Double findTotalBalance();

    //查询当日流水
    public List<OpRecord> findToday(String date);

}
