package com.yc;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.SQLException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AppMain.class})
@Slf4j
public class TestApp {
    @Autowired
    DataSource ds;

    @Test
    public void test1() throws SQLException {
        Assert.assertNotNull(ds.getConnection());
        log.info(ds.getConnection().toString());
    }
}
