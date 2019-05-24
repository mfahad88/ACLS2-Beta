package com.psl.fantasy.league.revamp.model.ui;

public class MyLeaderboardTabBean {
    private int userTeamId;
    private int userId;
    private String teamName;
    private String credit;

    public MyLeaderboardTabBean(int userTeamId, int userId, String teamName, String credit) {
        this.userTeamId = userTeamId;
        this.userId = userId;
        this.teamName = teamName;
        this.credit = credit;
    }

    public int getUserTeamId() {
        return userTeamId;
    }

    public void setUserTeamId(int userTeamId) {
        this.userTeamId = userTeamId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }
}
