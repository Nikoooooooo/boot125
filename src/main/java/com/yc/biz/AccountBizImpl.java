package com.yc.biz;

import com.yc.bean.Account;
import com.yc.bean.OpRecord;
import com.yc.bean.OpType;
import com.yc.mappers.AccountMapper;
import com.yc.mappers.OpRecordMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j2
@Transactional
@Primary
public class AccountBizImpl implements AccountBiz{
//    @Autowired
//    private AccountDao accountDao;
//    @Autowired
//    private OpRecordDao opRecordDao;

    @Autowired
    private AccountMapper accountDao;
    @Autowired
    private OpRecordMapper opRecordDao;

    @Transactional(readOnly = true)
    @Override
    public Account findAccount(int accountId) {
        return this.accountDao.selectById(accountId);
    }

    @Override
    public Account openAccount(double money) {
        //开户操作 返回新的账号的id
       // int accountid=this.accountDao.insert(money);
        Account newAccount=new Account();
        newAccount.setMoney(money);
        this.accountDao.insert(newAccount);

        //包装日志信息
        OpRecord opRecord=new OpRecord();
        opRecord.setAccountid(newAccount.getAccountid());  //取出新增的账号
        opRecord.setOpmoney(money);
        opRecord.setOpType(OpType.DEPOSITE);
        //this.opRecordDao.insertOpRecord(opRecord);
        this.opRecordDao.insert(opRecord);

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
            //a = this.accountDao.findById(accountId);
            a=this.accountDao.selectById(accountId);
        }catch (RuntimeException re){
            log.error(re.getMessage());  //TODO:封装保存日志的操作
            throw new RuntimeException("查无此账户"+accountId+"无法完成此操作");
        }
        //存款时 金额累加
        a.setMoney(a.getMoney()+money);

        //this.accountDao.update(accountId,a.getMoney());
        this.accountDao.updateById(a);

        OpRecord opRecord=new OpRecord();
        opRecord.setAccountid(accountId);
        opRecord.setOpmoney(money);
        if (transferid!=null){
            opRecord.setOpType(OpType.TRANSFER);
            opRecord.setTransferid(transferid);
        }else {
            opRecord.setOpType(OpType.DEPOSITE);
        }
        //this.opRecordDao.insertOpRecord(opRecord);
        this.opRecordDao.insert(opRecord);
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
            //a = this.accountDao.findById(accountId);
            a=this.accountDao.selectById(accountId);
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
            opRecord.setOpType(OpType.TRANSFER);
            opRecord.setTransferid(transferid);
        }else {
            opRecord.setOpType(OpType.WITHDRAW);
        }

        //this.opRecordDao.insertOpRecord(opRecord);  //先插入日志
        this.opRecordDao.insert(opRecord);
        //this.accountDao.update(accountId,a.getMoney()); //再减金额
        this.accountDao.updateById(a);
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

