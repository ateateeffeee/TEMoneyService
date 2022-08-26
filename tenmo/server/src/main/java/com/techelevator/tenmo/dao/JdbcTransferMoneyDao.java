package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.TransferMoney;
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
    private JdbcAccountDao jdbcAccountDao;

    public JdbcTransferMoneyDao(JdbcTemplate jdbcTemplate, JdbcAccountDao jdbcAccountDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcAccountDao =jdbcAccountDao;
    }

    @Override
    public int createTransfer(int senderId, int receiverId, BigDecimal transferAmount) {


        
            BigDecimal balance = jdbcAccountDao.getBalance(senderId);
            BigDecimal transfer = transferAmount;

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

//   if(jdbcAccountDao.getPrivateAccount(senderId).getBalance().compareTo(transferAmount)>0)
        //make this work with postman
        //how to update account objects????

        //how to update account objects if only passing ids?
        //how to send through postman with existing setup?
        //what to call bigDecimal value in JSON?

//        if(senderAccount.getBalance().compareTo(transferAmount) > 0){
//            senderAccount.setBalance(senderAccount.getBalance().subtract(transferAmount));
//            receiverAccount.setBalance(receiverAccount.getBalance().add(transferAmount));




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

    @Override
    public List<TransferMoney> getListOfTransfersForUserId(int id) {
        List<TransferMoney> transferList = new ArrayList<>();
        String sql = "Select transfer_id, sender_user_id, receiver_user_id, transfer_amount FROM transfer_money WHERE sender_user_id = ? OR receiver_user_id = ?;";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql,id,id);
        while (rowSet.next()){
            TransferMoney transferMoney = new TransferMoney();
            transferMoney = mapRowToTransferMoney(rowSet);

            transferList.add(transferMoney);
        }

        return transferList;
    }
    @Override
    public TransferMoney getTransferMoneyFromTransferId(int id){
        String sql = "SELECT transfer_id, sender_user_id, receiver_user_id, transfer_amount FROM transfer_money WHERE transfer_id = ?;";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql,id);
        if (rowSet.next()){
            return mapRowToTransferMoney(rowSet);
        }
        return null;
    }

    private TransferMoney mapRowToTransferMoney(SqlRowSet rowSet){
        TransferMoney transferMoney = new TransferMoney();
        transferMoney.setTransferId(rowSet.getInt("transfer_id"));
        transferMoney.setSenderId(rowSet.getInt("sender_user_id"));
        transferMoney.setReceiverId(rowSet.getInt("receiver_user_id"));
        transferMoney.setTransferAmount(rowSet.getBigDecimal("transfer_amount"));

        return transferMoney;
    }


}

