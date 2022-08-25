package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferMoneyDao implements TransferMoneyDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcTransferMoneyDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int createTransfer(int senderId, int receiverId, BigDecimal transferAmount) {

        //make this work with postman
        //how to update account objects????

        //how to update account objects if only passing ids?
        //how to send through postman with existing setup?
        //what to call bigDecimal value in JSON?

//        if(senderAccount.getBalance().compareTo(transferAmount) > 0){
//            senderAccount.setBalance(senderAccount.getBalance().subtract(transferAmount));
//            receiverAccount.setBalance(receiverAccount.getBalance().add(transferAmount));

        String sql = "INSERT INTO transfer_money(sender_user_id,receiver_user_id,transfer_amount)\n" +
                "VALUES(?,?,?) RETURNING transfer_id";
        Integer newId;

        //Update sender account
        String updateSenderSql = " UPDATE account SET balance = balance - ? \n" +
                "WHERE user_id = ?;";


        String updateReceiverSql = " UPDATE account SET balance = balance + ? \n" +
                "WHERE user_id = ?;";


        try {
            newId = jdbcTemplate.queryForObject(sql, Integer.class, senderId, receiverId, transferAmount);

            jdbcTemplate.update(updateSenderSql, transferAmount, senderId);
            jdbcTemplate.update(updateReceiverSql, transferAmount, receiverId);
            return newId;

        } catch (DataAccessException e) {
            System.out.println("Error");
        }


        return 0;
    }

    @Override
    public List<User> getListOfIdsAndUsernames() {
        List<User> userList = new ArrayList<>();
        String sql = "SELECT user_id, username FROM tenmo_user";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql);
        while (rowSet.next()){

            User user = new User();
            user.setId(rowSet.getInt("user_id"));
            user.setUsername(rowSet.getString("username"));

            userList.add(user);


        }
        //TODO:this returns user object with nulls, want to only return name and id

        return userList;
    }


}

