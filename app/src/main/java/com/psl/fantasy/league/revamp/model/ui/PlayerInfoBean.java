package com.psl.fantasy.league.revamp.model.ui;

public class PlayerInfoBean {
    private int Id;
    private String playerName;
    private String credit;
    private int captain;
    private int vice_captain;

    public PlayerInfoBean(int id, String playerName, String credit, int captain, int vice_captain) {
        Id = id;
        this.playerName = playerName;
        this.credit = credit;
        this.captain = captain;
        this.vice_captain = vice_captain;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public int getCaptain() {
        return captain;
    }

    public void setCaptain(int captain) {
        this.captain = captain;
    }

    public int getVice_captain() {
        return vice_captain;
    }

    public void setVice_captain(int vice_captain) {
        this.vice_captain = vice_captain;
    }
}
