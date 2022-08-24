package com.techelevator.tenmo.controller;


import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.model.Account;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private AccountDao accountDao;

    public AccountController(AccountDao accountDao) {this.accountDao =accountDao;}

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public BigDecimal get(@PathVariable int id) {
        return accountDao.getBalance(id);
    }

}
