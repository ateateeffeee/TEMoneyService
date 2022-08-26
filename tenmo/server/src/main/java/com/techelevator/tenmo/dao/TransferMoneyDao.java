package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;

import java.math.BigDecimal;
import java.util.List;

public interface TransferMoneyDao {

    int createTransfer(int senderId, int receiverId, BigDecimal transferAmount);

    List<User> getListOfIdsAndUsernames();



}
