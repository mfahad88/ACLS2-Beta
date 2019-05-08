package com.psl.fantasy.league.model.ui;

public class PrizesBean {
    private String prizeName;
    private String prizeDesc;
    private int quantity;
    private int consumed;

    public PrizesBean(String prizeName, String prizeDesc, int quantity, int consumed) {
        this.prizeName = prizeName;
        this.prizeDesc = prizeDesc;
        this.quantity = quantity;
        this.consumed = consumed;
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
}
