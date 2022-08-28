package com.techelevator.dao;

import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.dao.JdbcUserDao;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;

public class JdbcAccountDaoTests extends BaseDaoTests {

     private JdbcAccountDao sut;
     private JdbcUserDao userSut;


     @Before
    public void setup(){
         JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
         sut = new JdbcAccountDao(jdbcTemplate);
         userSut= new JdbcUserDao(jdbcTemplate);
     }

     @Test
    public void should_return_balance_equal_to_1000(){
         userSut.create("hankhill","password");
         Assert.assertEquals(new BigDecimal("1000.00"),sut.getBalance(1003));
     }

//     @Test
//    public void should_get_private_account(){
//         userSut.create("hankhill1","password");
//         Assert.assertEquals(2001,userSut);
//
//     }

}

