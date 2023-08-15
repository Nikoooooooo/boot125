package com.yc.dao;


import com.yc.AppMain;
import com.yc.bean.Account;
import com.yc.config.Configs;
import junit.framework.TestCase;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AppMain.class})
@Slf4j
public class AccountDaoJdbcTemplateImplTest extends TestCase {

    @Autowired
    private AccountDao accountDao;


    @Test
    public void update() {
        accountDao.update(3,10);
    }

    @Test
    public void findCount() {
        int total= accountDao.findCount();;
        Assert.assertEquals(3,total);
    }

    @Test
    public void findAll() {
        List<Account> list=this.accountDao.findAll();
        log.info(list.toString());
    }

    @Test
    public void findById() {
        Account account=this.accountDao.findById(6);
        log.info(account.toString());
    }

    @Test
    public void insert() {
        int accountid= accountDao.insert(100);
        log.info("新开账户为:"+accountid);
        Assert.assertNotNull(accountid);
    }

    @Test
    public void delete() {
        accountDao.delete(32);
    }





}