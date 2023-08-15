package com.yc;

import org.junit.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.Assert;

import javax.sql.DataSource;

@SpringBootTest
@ActiveProfiles("init")   //表示启用application-init.yml这个profile
public class Testinit {
    @Autowired
    private DataSource ds;

    @Test
    public void testDataSource(){
        Assert.notNull(ds);
        System.out.println(ds);
    }
}
