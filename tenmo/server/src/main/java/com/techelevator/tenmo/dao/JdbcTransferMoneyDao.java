package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class JdbcTransferMoneyDao implements TransferMoneyDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcTransferMoneyDao(JdbcTemplate jdbcTemplate){this.jdbcTemplate = jdbcTemplate;}

    @Override
    public int createTransfer(Account senderAccount, Account receiverAccount, BigDecimal transferAmount) {
        if(senderAccount.getBalance().compareTo(transferAmount)>0){
            senderAccount.setBalance(senderAccount.getBalance().subtract(transferAmount));
            receiverAccount.setBalance(receiverAccount.getBalance().add(transferAmount));

        String sql = "INSERT INTO transfer_money(sender_user_id,receiver_user_id,transfer_amount)\n" +
                "VALUES(?,?,?) RETURNING transfer_id";
        Integer newId;

        //Update sender account
            String updateSenderSql = " UPDATE account SET balance = ? \n" +
                    "WHERE user_id = ?;";


            String updateReceiverSql = " UPDATE account SET balance = ? \n" +
                    "WHERE user_id = ?;";


       try {
           newId = jdbcTemplate.queryForObject(sql, Integer.class, senderAccount.getUser_id(), receiverAccount.getUser_id(), transferAmount);

           jdbcTemplate.update(updateSenderSql,senderAccount.getBalance(),senderAccount.getUser_id());
           jdbcTemplate.update(updateReceiverSql,receiverAccount.getBalance(),receiverAccount.getUser_id());
           return newId;

       }catch (DataAccessException e){
           System.out.println("Error");}

        }


        return 0;
    }
}
