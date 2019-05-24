package com.psl.fantasy.league.revamp.model.ui;

public class ContestBean {
    private int contestId;
    private String price;
    private int progress;
    private String spots_left;
    private String spots;
    private String winners;
    private String discount;
    private String actual_price;
    private String multiple;
    private String confirm_winning;
    private String contest_type;


    public ContestBean(int contestId, String price, int progress, String spots_left, String spots, String winners, String discount, String actual_price, String multiple, String confirm_winning, String contest_type) {
        this.contestId = contestId;
        this.price = price;
        this.progress = progress;
        this.spots_left = spots_left;
        this.spots = spots;
        this.winners = winners;
        this.discount = discount;
        this.actual_price = actual_price;
        this.multiple = multiple;
        this.confirm_winning = confirm_winning;
        this.contest_type = contest_type;
    }

    public String getSpots() {
        return spots;
    }

    public void setSpots(String spots) {
        this.spots = spots;
    }

    public int getContestId() {
        return contestId;
    }

    public void setContestId(int contestId) {
        this.contestId = contestId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public String getSpots_left() {
        return spots_left;
    }

    public void setSpots_left(String spots_left) {
        this.spots_left = spots_left;
    }

    public String getWinners() {
        return winners;
    }

    public void setWinners(String winners) {
        this.winners = winners;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getActual_price() {
        return actual_price;
    }

    public void setActual_price(String actual_price) {
        this.actual_price = actual_price;
    }

    public String getMultiple() {
        return multiple;
    }

    public void setMultiple(String multiple) {
        this.multiple = multiple;
    }

    public String getConfirm_winning() {
        return confirm_winning;
    }

    public void setConfirm_winning(String confirm_winning) {
        this.confirm_winning = confirm_winning;
    }

    public String getContest_type() {
        return contest_type;
    }

    public void setContest_type(String contest_type) {
        this.contest_type = contest_type;
    }

    @Override
    public String toString() {
        return "ContestBean{" +
                "contestId=" + contestId +
                ", price='" + price + '\'' +
                ", progress=" + progress +
                ", spots_left='" + spots_left + '\'' +
                ", winners='" + winners + '\'' +
                ", discount='" + discount + '\'' +
                ", actual_price='" + actual_price + '\'' +
                ", multiple='" + multiple + '\'' +
                ", confirm_winning='" + confirm_winning + '\'' +
                ", contest_type='" + contest_type + '\'' +
                '}';
    }
}