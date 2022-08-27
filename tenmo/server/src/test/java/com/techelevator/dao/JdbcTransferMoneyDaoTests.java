package com.techelevator.dao;

import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.dao.JdbcTransferMoneyDao;
import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.dao.TransferMoneyDao;
import com.techelevator.tenmo.model.TransferMoney;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.math.BigDecimal;

public class JdbcTransferMoneyDaoTests extends BaseDaoTests {

    private JdbcTransferMoneyDao sut;
    private JdbcUserDao userSut;

//    public JdbcTransferMoneyDao(DataSource dataSource){
//        jdbcTemplate = new JdbcTemplate(dataSource);
//    }

    //private TransferMoney test = new TransferMoney();

    @Before
    public void setup(){
        //sut = new JdbcTransferMoneyDao(dataSource);
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource); //why datasource?
        JdbcAccountDao jdbcAccountDao = new JdbcAccountDao(jdbcTemplate); //what does it want?

        sut = new JdbcTransferMoneyDao(jdbcTemplate, jdbcAccountDao); //this needs both

        userSut = new JdbcUserDao(jdbcTemplate);
        //test = new TransferMoney(1001,1002,new BigDecimal("100"),"Approved");
    }

    @Test
    public void createNewTransfer() {
        //userSut.create("user","password");
        int transferId = sut.createTransfer(1001,1002, new BigDecimal("100"), "Approved");


        //TransferMoney transferCreated = sut.createTransfer(test);
        Assert.assertEquals(3001,transferId);
    }


}
