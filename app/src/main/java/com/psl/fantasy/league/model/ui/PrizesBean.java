package com.psl.fantasy.league.model.ui;

public class PrizesBean {
    private int prizeId;
    private int catId;
    private int userId;
    private String prizeName;
    private String prizeDesc;
    private String amount;
    private int quantity;
    private int consumed;
    private String cash;

    public PrizesBean(int prizeId,int userId,int catId,String prizeName, String prizeDesc, String amount, int quantity, int consumed, String cash) {
        this.prizeId=prizeId;
        this.userId=userId;
        this.catId=catId;
        this.prizeName = prizeName;
        this.prizeDesc = prizeDesc;
        this.amount = amount;
        this.quantity = quantity;
        this.consumed = consumed;
        this.cash = cash;
    }

    public int getPrizeId() {
        return prizeId;
    }

    public void setPrizeId(int prizeId) {
        this.prizeId = prizeId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCatId() {
        return catId;
    }

    public void setCatId(int catId) {
        this.catId = catId;
    }

    public String getPrizeName() {
        return prizeName;
    }

    public void setPrizeName(String prizeName) {
        this.prizeName = prizeName;
    }

    public String getPrizeDesc() {
        return prizeDesc;
    }

    public void setPrizeDesc(String prizeDesc) {
        this.prizeDesc = prizeDesc;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getConsumed() {
        return consumed;
    }

    public void setConsumed(int consumed) {
        this.consumed = consumed;
    }

    public String getCash() {
        return cash;
    }

    public void setCash(String cash) {
        this.cash = cash;
    }
}
