package com.techelevator.tenmo.model;

import javax.validation.constraints.Min;
import java.math.BigDecimal;

public class TransferMoney {

    private int transferId;

    private  int senderId;

    private int receiverId;

    @Min(value = 1 , message = "Can't transfer zero or negative amount")
    private BigDecimal transferAmount;

    private String status;

    public TransferMoney(){};

    public TransferMoney(int transferId, int senderId, int receiverId, BigDecimal transferAmount, String status) {
        this.transferId = transferId;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.transferAmount = transferAmount;
        this.status = status;
    }

    public int getTransferId() {
        return transferId;
    }

    public void setTransferId(int transferId) {
        this.transferId = transferId;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }

    public BigDecimal getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(BigDecimal transferAmount) {
        this.transferAmount = transferAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
