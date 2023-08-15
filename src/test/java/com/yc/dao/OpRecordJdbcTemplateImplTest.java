package com.yc.dao;

import com.yc.AppMain;
import com.yc.bean.OpRecord;
import com.yc.bean.OpType;

import com.yc.config.Configs;
import lombok.extern.log4j.Log4j2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Configs.class})
@Log4j2
public class OpRecordJdbcTemplateImplTest {
    @Autowired
    private OpRecordDao opRecordDao;

    @Test
    public void insertOpRecord() {
        OpRecord opRecord=new OpRecord();
        opRecord.setAccountid(6);
        opRecord.setOpmoney(5);
        opRecord.setOpType(OpType.DEPOSITE);
        this.opRecordDao.insertOpRecord(opRecord);
    }

    @Test
    public void findOpRecord() {
        List<OpRecord> list=this.opRecordDao.findOpRecord(1);
        System.out.println(list);
    }

    @Test
    public void testFindOpRecord() {
        List<OpRecord> list=this.opRecordDao.findOpRecord(6,"DEPOSITE");
        System.out.println(list);
    }

    @Test
    public void testFindOpRecord1() {
    }
}