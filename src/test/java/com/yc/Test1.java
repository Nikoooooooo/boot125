package com.yc;

import com.yc.bean.Account;
import com.yc.config.Configs;
import com.yc.mappers.AccountMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Configs.class})
@Slf4j
public class Test1 {
    @Autowired
    AccountMapper accountMapper;

    @Test
    public  void testMyjob(){
        System.out.println(accountMapper.selectRaw());
    }
}
