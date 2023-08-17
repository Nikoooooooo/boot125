package com.yc.dao;

import com.yc.bean.OpRecord;
import com.yc.bean.OpType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

//@Repository
public class OpRecordJdbcTemplateImpl implements OpRecordDao{

   // @Autowired
    private JdbcTemplate jdbcTemplate;



    @Override
    public void insertOpRecord(OpRecord opRecord) {
        String sql="insert into oprecord(accountid,opmoney,optime,optype,transferid) values(?,?,now(),?,?)";

        this.jdbcTemplate.update(sql,opRecord.getAccountid(),opRecord.getOpmoney(),
                opRecord.getOptype().getKey(),opRecord.getTransferid());
    }

    @Override
    public List<OpRecord> findOpRecord(int accountid) {
        List<OpRecord> list=this.jdbcTemplate.query(
                "select * from oprecord where accountid=? order by optime desc",
                (resultSet,rowNum)->{
                    OpRecord opRecord=new OpRecord();
                    opRecord.setId(resultSet.getInt(1));
                    opRecord.setAccountid(resultSet.getInt(2));
                    opRecord.setOpmoney(resultSet.getDouble(3));
                    opRecord.setOptime(resultSet.getString(4));

                    String optype=resultSet.getString(5);
                    if (optype.equalsIgnoreCase("withdraw")){
                        opRecord.setOptype(OpType.WITHDRAW);
                    }else if (optype.equalsIgnoreCase("deposite")){
                        opRecord.setOptype(OpType.DEPOSITE);
                    }else {
                        opRecord.setOptype(OpType.TRANSFER);
                    }
                    opRecord.setTransferid(resultSet.getInt(6));
                    return opRecord;
                },accountid);
        return list;
    }

    @Override
    public List<OpRecord> findOpRecord(int accountid, String opType) {
        List<OpRecord> list=this.jdbcTemplate.query(
                "select * from oprecord where accountid=? and opType=? order by optime desc",
                (resultSet,rowNum) ->{
                    OpRecord opRecord=new OpRecord();
                    opRecord.setId(resultSet.getInt(1));
                    opRecord.setAccountid(resultSet.getInt(2));
                    opRecord.setOpmoney(resultSet.getDouble(3));
                    opRecord.setOptime(resultSet.getString(4));

                    String optype=resultSet.getString(5);
                    if (optype.equalsIgnoreCase("withdraw")){
                        opRecord.setOptype(OpType.WITHDRAW);
                    }else if (optype.equalsIgnoreCase("deposite")){
                        opRecord.setOptype(OpType.DEPOSITE);
                    }else {
                        opRecord.setOptype(OpType.TRANSFER);
                    }
                    opRecord.setTransferid(resultSet.getInt(6));
                    return opRecord;
                },accountid,opType);
        return list;
    }

    @Override
    public List<OpRecord> findOpRecord(OpRecord opRecord) {
        return null;
    }
}
