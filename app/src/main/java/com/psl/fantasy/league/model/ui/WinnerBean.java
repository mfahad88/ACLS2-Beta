package com.psl.fantasy.league.model.ui;

public class WinnerBean {
    private String rank;
    private String price;

    public WinnerBean(String rank, String price) {
        this.rank = rank;
        this.price = price;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }


}
