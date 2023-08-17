package com.yc.biz;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yc.bean.Account;
import com.yc.bean.OpRecord;
import com.yc.bean.OpType;
import com.yc.mappers.AccountMapper;
import com.yc.mappers.OpRecordMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@Log4j2
@Transactional
@Primary
public class AccountBizImpl implements AccountBiz{
//    @Autowired
//    private accountMapper accountMapper;
//    @Autowired
//    private opRecordMapper opRecordMapper;

    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private OpRecordMapper opRecordMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Transactional(readOnly = true)
    @Override
    public Account findAccount(int accountId) {
        return this.accountMapper.selectById(accountId);
    }

    @Override
    public Double findTotalBalance() {
        QueryWrapper<Account> wrapper1=new QueryWrapper<>();
        wrapper1.select("sum(balance) as total");
        List<Map<String,Object>> list=accountMapper.selectMaps(wrapper1);
        if (list!=null &&list.size()>0){
            Map<String,Object> map=list.get(0);
            if (map.containsKey("total")){
                return Double.parseDouble(map.get("total").toString());
            }
        }
        return 0.0;
    }

    @Override
    public List<OpRecord> findToday(String date) {
      List<OpRecord> list=new ArrayList<>();
      //验证date格式是否正确
        DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
        Date d=null;
        try {
            d=df.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            log.error("待查询的日志格式不正确,原格式:"+date+",需要的格式为:yyyy-MM-dd");
            throw  new RuntimeException(e);
        }
        //先查缓存,缓存有则取缓存中的
        if (redisTemplate.hasKey(date)){
            //redisTemplate.opsForList().size(date)  查看date键下有多少条数据
           // redisTemplate.opsForList().range(键,0,取出数据的条数);

            list=redisTemplate.opsForList().range(date,0,redisTemplate.opsForList().size(date));
            log.info("从缓存的键:"+date+",取出的值list为:"+list);
            return list;
        }
        //再查数据库
        QueryWrapper wrapper=new QueryWrapper();
        //计算后一天
        Calendar cal=Calendar.getInstance();
        cal.setTime(d);
        cal.add(Calendar.DATE,1);
        Date nextDate=cal.getTime();
        String nextDateString=df.format(nextDate);
        wrapper.between("optime",date,nextDateString);
        list=opRecordMapper.selectList(wrapper);
        if (list!=null &&list.size()>0){
            //存入缓存中
            redisTemplate.delete(date); //清空原来的
            redisTemplate.opsForList().leftPush(date,list);
            redisTemplate.expire(date,15, TimeUnit.DAYS);  //15天的有效期

        }
        return list;
    }

    @Override
    public Account openAccount(double money) {
        //开户操作 返回新的账号的id
       // int accountid=this.accountMapper.insert(money);
        Account newAccount=new Account();
        newAccount.setMoney(money);
        this.accountMapper.insert(newAccount);

        //包装日志信息
        OpRecord opRecord=new OpRecord();
        opRecord.setAccountid(newAccount.getAccountid());  //取出新增的账号
        opRecord.setOpmoney(money);
        opRecord.setOptype(OpType.DEPOSITE);
        //this.opRecordMapper.insertOpRecord(opRecord);
        this.opRecordMapper.insert(opRecord);

        //返回新的账户信息
//        Account a=new Account();
//        a.setAccountid(accountid);
//        a.setMoney(money);
        return newAccount;
    }




    @Override
    public Account deposite(int accountId, double money) {
        return this.deposite(accountId,money,null);
    }

    @Override
    public Account deposite(int accountId, double money, Integer transferid) {
        Account a=null;
        try {
            //a = this.accountMapper.findById(accountId);
            a=this.accountMapper.selectById(accountId);
        }catch (RuntimeException re){
            log.error(re.getMessage());  //TODO:封装保存日志的操作
            throw new RuntimeException("查无此账户"+accountId+"无法完成此操作");
        }
        //存款时 金额累加
        a.setMoney(a.getMoney()+money);

        //this.accountMapper.update(accountId,a.getMoney());
        this.accountMapper.updateById(a);

        OpRecord opRecord=new OpRecord();
        opRecord.setAccountid(accountId);
        opRecord.setOpmoney(money);
        if (transferid!=null){
            opRecord.setOptype(OpType.TRANSFER);
            opRecord.setTransferid(transferid);
        }else {
            opRecord.setOptype(OpType.DEPOSITE);
        }
        //this.opRecordMapper.insertOpRecord(opRecord);
        this.opRecordMapper.insert(opRecord);
        return a;

    }

    @Override
    public Account withdraw(int accountId, double money) {
        return this.withdraw(accountId,money,null);
    }

    @Override
    public Account withdraw(int accountId, double money, Integer transferid) {
        Account a=null;
        try {
            //a = this.accountMapper.findById(accountId);
            a=this.accountMapper.selectById(accountId);
        }catch (RuntimeException re){
            log.error(re.getMessage());  //TODO:封装保存日志的操作
            throw new RuntimeException("查无此账户"+accountId+"无法完成此操作");
        }
        //存款时 金额累加
        a.setMoney(a.getMoney()-money);
        OpRecord opRecord=new OpRecord();
        opRecord.setAccountid(accountId);
        opRecord.setOpmoney(money);
        if (transferid!=null){
            opRecord.setOptype(OpType.TRANSFER);
            opRecord.setTransferid(transferid);
        }else {
            opRecord.setOptype(OpType.WITHDRAW);
        }

        //this.opRecordMapper.insertOpRecord(opRecord);  //先插入日志
        this.opRecordMapper.insert(opRecord);
        //this.accountMapper.update(accountId,a.getMoney()); //再减金额
        this.accountMapper.updateById(a);
        return a;

    }

    @Override
    public Account transfer(int accountId, double money, int toaccountId) {
        //从accountId转money到 toAccountId
        this.deposite(toaccountId,money,accountId); //收款方
        //accountid从账户中取money
        Account a=this.withdraw(accountId,money,toaccountId);
        return a;
    }


}

