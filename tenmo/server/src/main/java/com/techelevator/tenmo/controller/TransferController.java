package com.techelevator.tenmo.controller;


import com.techelevator.tenmo.dao.TransferMoneyDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.TransferMoney;
import com.techelevator.tenmo.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@PreAuthorize("isAuthenticated()")
@RestController
@RequestMapping("/transfers")
public class TransferController {

    private TransferMoneyDao transferMoneyDao;

    public TransferController(TransferMoneyDao transferMoneyDao) {
        this.transferMoneyDao = transferMoneyDao;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST)
    public BigDecimal create(@Valid @RequestBody TransferMoney transferMoney){
        transferMoneyDao.createTransfer(transferMoney.getSenderId(),transferMoney.getReceiverId(),transferMoney.getTransferAmount());

        return transferMoney.getTransferAmount();
    }

    @RequestMapping(path = "/users", method = RequestMethod.GET )
    public List<User> getUsers(){
        //don't show logged in user
        return transferMoneyDao.getListOfIdsAndUsernames();
    }

}
