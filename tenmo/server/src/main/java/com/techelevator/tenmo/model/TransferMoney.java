package com.techelevator.tenmo.model;

import javax.validation.constraints.Min;

public class TransferMoney {

    private int transferId;

    private  int senderId;

    private int receiverId;

    @Min(value = 1 , message = "Can't transfer zero or negative amount")
    private int transferAmount;

    public TransferMoney(int transferId, int senderId, int receiverId, int transferAmount) {
        this.transferId = transferId;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.transferAmount = transferAmount;
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

    public int getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(int transferAmount) {
        this.transferAmount = transferAmount;
    }
}
