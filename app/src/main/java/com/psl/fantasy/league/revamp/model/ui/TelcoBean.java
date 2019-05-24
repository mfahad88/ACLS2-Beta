package com.psl.fantasy.league.revamp.model.ui;

public class TelcoBean {
    private int id;
    private String telcoName;

    public TelcoBean(int id, String telcoName) {
        this.id = id;
        this.telcoName = telcoName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTelcoName() {
        return telcoName;
    }

    public void setTelcoName(String telcoName) {
        this.telcoName = telcoName;
    }

    @Override
    public String toString() {
        return "TelcoBean{" +
                "id=" + id +
                ", telcoName='" + telcoName + '\'' +
                '}';
    }
}
