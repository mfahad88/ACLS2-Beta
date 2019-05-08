package com.psl.fantasy.league.model.ui;

public class JoinedContestBean {
    private int contestId;
    private int userId;
    private String contestName;
    private String contestDesc;
    private int entryFee;
    private String teamName;
    private double points;

    public JoinedContestBean(int contestId, int userId, String contestName, String contestDesc, int entryFee, String teamName, double points) {
        this.contestId = contestId;
        this.userId = userId;
        this.contestName = contestName;
        this.contestDesc = contestDesc;
        this.entryFee = entryFee;
        this.teamName = teamName;
        this.points = points;
    }

    public int getContestId() {
        return contestId;
    }

    public void setContestId(int contestId) {
        this.contestId = contestId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getContestName() {
        return contestName;
    }

    public void setContestName(String contestName) {
        this.contestName = contestName;
    }

    public String getContestDesc() {
        return contestDesc;
    }

    public void setContestDesc(String contestDesc) {
        this.contestDesc = contestDesc;
    }

    public int getEntryFee() {
        return entryFee;
    }

    public void setEntryFee(int entryFee) {
        this.entryFee = entryFee;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public double getPoints() {
        return points;
    }

    public void setPoints(double points) {
        this.points = points;
    }
}
