package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;

import java.math.BigDecimal;

public interface TransferMoneyDao {

    int createTransfer(Account senderAccount, Account receiverAccount, BigDecimal transferAmount);
}
