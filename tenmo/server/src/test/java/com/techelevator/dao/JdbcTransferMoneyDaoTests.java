package com.techelevator.dao;

import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.dao.JdbcTransferMoneyDao;
import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.dao.TransferMoneyDao;
import com.techelevator.tenmo.model.TransferMoney;
import com.techelevator.tenmo.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.List;

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
//        sut.createTransfer(1001,1002,new BigDecimal("500"),"Approved");
//        sut.createTransfer(1001,1002,new BigDecimal("500"),"Approved");
    }

    @Test
    public void should_create_new_transfer() {
        //userSut.create("user","password");
        int transferId = sut.createTransfer(1001,1002, new BigDecimal("100"), "Approved");


        //TransferMoney transferCreated = sut.createTransfer(test);
        Assert.assertEquals(3001,transferId);
        //TEST PASSES ON ITS OWN NOT TOGETHER
    }
    @Test
    public void should_get_list_of_ids_and_username(){
        List<User> userList = sut.getListOfIdsAndUsernames();
         String actual = userList.get(0).getUsername();
        Assert.assertEquals("bob", actual);
        Assert.assertEquals(2,userList.size());
    }
   @Test
   public void should_return_transfers_from_user_id(){
        sut.createTransfer(1001,1002,new BigDecimal("500"),"Approved");
       sut.createTransfer(1001,1002,new BigDecimal("500"),"Approved");
        List<TransferMoney> transferMoneyList =sut.getListOfTransfersForUserId(1001);
        Assert.assertEquals(2,transferMoneyList.size());


   }



    @Test
    public void should_return_transfer_money_from_transfer_id(){
        sut.createTransfer(1001,1002,new BigDecimal("500"),"Approved");
        Assert.assertEquals(1001,sut.getTransferMoneyFromTransferId(3001).getSenderId());
    }


}
