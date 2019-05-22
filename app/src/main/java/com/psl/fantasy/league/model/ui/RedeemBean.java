package com.psl.fantasy.league.model.ui;

public class RedeemBean {
    private int redeemId;
    private String teamName;
    private String matchName;
    private String earnPoints;
    private int teamId;

    public RedeemBean(int redeemId, String teamName, String matchName, String earnPoints, int teamId) {
        this.redeemId = redeemId;
        this.teamName = teamName;
        this.matchName = matchName;
        this.earnPoints = earnPoints;
        this.teamId = teamId;
    }

    public int getRedeemId() {
        return redeemId;
    }

    public void setRedeemId(int redeemId) {
        this.redeemId = redeemId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getMatchName() {
        return matchName;
    }

    public void setMatchName(String matchName) {
        this.matchName = matchName;
    }

    public String getEarnPoints() {
        return earnPoints;
    }

    public void setEarnPoints(String earnPoints) {
        this.earnPoints = earnPoints;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }
}
