package com.techelevator.tenmo.controller;


import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.dao.TransferMoneyDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.TransferMoney;
import com.techelevator.tenmo.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@PreAuthorize("isAuthenticated()")
@RestController
@RequestMapping("/transfers")
public class TransferController {

    private TransferMoneyDao transferMoneyDao;
    private JdbcAccountDao jdbcAccountDao;
    private UserDao userDao;

    public TransferController(TransferMoneyDao transferMoneyDao,JdbcAccountDao jdbcAccountDao, UserDao userDao) {
        this.transferMoneyDao = transferMoneyDao;
        this.jdbcAccountDao = jdbcAccountDao;
        this.userDao = userDao;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST)
    public BigDecimal create(@Valid @RequestBody TransferMoney transferMoney){
        if(transferMoney.getReceiverId()== transferMoney.getSenderId()){
            return new BigDecimal("0");
        }
        if(jdbcAccountDao.getBalance(transferMoney.getSenderId()).compareTo(transferMoney.getTransferAmount())>=0){
            transferMoneyDao.createTransfer(transferMoney.getSenderId(),transferMoney.getReceiverId(),transferMoney.getTransferAmount());
            return transferMoney.getTransferAmount();
        }
        return new BigDecimal("0");
    }

    @RequestMapping(path = "/users", method = RequestMethod.GET )
    public List<User> getUsers(){
        //don't show logged in user
        return transferMoneyDao.getListOfIdsAndUsernames();
    }

    @RequestMapping(path = "", method = RequestMethod.GET)
    public List<TransferMoney> getTransfers(Principal principal){

        int userId = userDao.findIdByUsername(principal.getName());


        return transferMoneyDao.getListOfTransfersForUserId(userId);
    }

}
