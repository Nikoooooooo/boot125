package com.yc.dao;

import com.yc.bean.Account;

import java.util.List;

public interface AccountDao {

    //添加账号
    public int insert(double money);

    //根据账号将money更新
    public void update(int accountid,double money);

    //删除账号
    public void delete(int accountid);

    //查询账户总数
    public int findCount();

    //查询所有的账户
    public List<Account> findAll();

    //根据id查询账号
    public Account findById(int accountid);

}
